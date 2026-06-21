package com.example.order_saga.orchestrator.service;

import com.example.order_saga.orchestrator.dto.InventoryReservedEvent;
import com.example.order_saga.orchestrator.dto.OrderCreatedEvent;
import com.example.order_saga.orchestrator.dto.ProcessPaymentCommand;
import com.example.order_saga.orchestrator.dto.ReserveInventoryCommand;
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
    SagaInstance saga =
        sagaInstanceRepository
            .findByOrderId(inventoryReservedEvent.orderId())
            .orElseThrow(
                () ->
                    new RuntimeException(
                        "Saga not found for orderId=" + inventoryReservedEvent.orderId()));

    saga.setCurrentStep("INVENTORY_RESERVED");
    sagaInstanceRepository.save(saga);

    sagaEventService.logEvent(saga.getSagaId(), "INVENTORY_RESERVED", inventoryReservedEvent);

    ProcessPaymentCommand command =
        new ProcessPaymentCommand(
            inventoryReservedEvent.orderId(), inventoryReservedEvent.amount());

    sagaEventService.logEvent(saga.getSagaId(), "PROCESS_PAYMENT_COMMAND", command);

    producer.publishProcessPayment(command);
  }
}
