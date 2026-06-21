package com.example.order_saga.orchestrator.service;

import com.example.order_saga.orchestrator.dto.*;
import com.example.order_saga.orchestrator.entity.SagaInstance;
import com.example.order_saga.orchestrator.producer.SagaCommandProducer;
import com.example.order_saga.orchestrator.repository.SagaInstanceRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SagaCoordinatorService {
  private final SagaInstanceRepository sagaInstanceRepository;

  private final SagaCommandProducer producer;

  private final SagaEventService sagaEventService;

  public void handleOrderCreated(OrderCreatedEvent orderCreatedEvent) {
    SagaInstance sagaInstance =
        SagaInstance.builder()
            .orderId(orderCreatedEvent.orderId())
            .currentStep("ORDER_CREATED")
            .status("IN_PROGRESS")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    SagaInstance savedSaga = sagaInstanceRepository.save(sagaInstance);

    sagaEventService.logEvent(savedSaga.getSagaId(), "ORDER_CREATED", orderCreatedEvent);

    ReserveInventoryCommand command =
        new ReserveInventoryCommand(
            orderCreatedEvent.orderId(),
            orderCreatedEvent.productId(),
            orderCreatedEvent.quantity(),
            orderCreatedEvent.amount());

    sagaEventService.logEvent(savedSaga.getSagaId(), "RESERVE_INVENTORY_COMMAND", command);

    producer.publishReserveInventory(command);
  }

  public void handleInventoryReserved(InventoryReservedEvent inventoryReservedEvent) {
    SagaInstance saga = findByOrderId(inventoryReservedEvent.orderId());

    saga.setCurrentStep("INVENTORY_RESERVED");
    sagaInstanceRepository.save(saga);

    sagaEventService.logEvent(saga.getSagaId(), "INVENTORY_RESERVED", inventoryReservedEvent);

    ProcessPaymentCommand command =
        new ProcessPaymentCommand(
            inventoryReservedEvent.orderId(), inventoryReservedEvent.amount());

    sagaEventService.logEvent(saga.getSagaId(), "PROCESS_PAYMENT_COMMAND", command);

    producer.publishProcessPayment(command);
  }

  public void handlePaymentSucceeded(PaymentCompletedEvent paymentCompletedEvent) {

    SagaInstance saga = findByOrderId(paymentCompletedEvent.orderId());

    saga.setCurrentStep("PAYMENT_COMPLETED");

    sagaInstanceRepository.save(saga);

    sagaEventService.logEvent(saga.getSagaId(), "PAYMENT_SUCCEEDED", paymentCompletedEvent);

    ProcessShipmentCommand processShipmentCommand =
        new ProcessShipmentCommand(paymentCompletedEvent.orderId());

    sagaEventService.logEvent(saga.getSagaId(), "CREATE_SHIPMENT_COMMAND", processShipmentCommand);

    producer.publishProcessShipment(processShipmentCommand);
  }

  public void handleShipmentCreated(ShipmentCreatedEvent shipmentCreatedEvent) {

    SagaInstance saga = findByOrderId(shipmentCreatedEvent.orderId());

    saga.setCurrentStep("SHIPMENT_CREATED");

    saga.setStatus("COMPLETED");

    sagaInstanceRepository.save(saga);

    sagaEventService.logEvent(saga.getSagaId(), "SHIPMENT_CREATED", shipmentCreatedEvent);

    sagaEventService.logEvent(saga.getSagaId(), "SAGA_COMPLETED", shipmentCreatedEvent);
  }

  private SagaInstance findByOrderId(Long orderId) {

    return sagaInstanceRepository
        .findByOrderId(orderId)
        .orElseThrow(() -> new RuntimeException("Saga not found for orderId=" + orderId));
  }
}
