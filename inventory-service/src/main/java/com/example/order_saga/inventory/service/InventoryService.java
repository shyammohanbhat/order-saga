package com.example.order_saga.inventory.service;

import com.example.order_saga.inventory.dto.InventoryReleasedEvent;
import com.example.order_saga.inventory.dto.InventoryReservedEvent;
import com.example.order_saga.inventory.dto.ReleaseInventoryCommand;
import com.example.order_saga.inventory.dto.ReserveInventoryCommand;
import com.example.order_saga.inventory.entity.InventoryReservation;
import com.example.order_saga.inventory.producer.InventoryReleaseEventProducer;
import com.example.order_saga.inventory.producer.InventoryReservedEventProducer;
import com.example.order_saga.inventory.repository.InventoryReservationRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {

  private final InventoryReservationRepository inventoryReservationRepository;
  private final InventoryReservedEventProducer inventoryReservedEventProducer;
  private final InventoryReleaseEventProducer inventoryReleaseEventProducer;

  public void saveInventoryReservation(ReserveInventoryCommand reserveInventoryCommand) {
    InventoryReservation inventoryReservation =
        InventoryReservation.builder()
            .orderId(reserveInventoryCommand.orderId())
            .productId(reserveInventoryCommand.productId())
            .quantity(reserveInventoryCommand.quantity())
            .status("RESERVED")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    Long reservationId =
        inventoryReservationRepository.save(inventoryReservation).getReservationId();

    InventoryReservedEvent inventoryReservedEvent =
        new InventoryReservedEvent(
            reserveInventoryCommand.orderId(), reservationId, reserveInventoryCommand.amount());
    inventoryReservedEventProducer.publishReserveInventoryEvent(inventoryReservedEvent);
  }

  public void releaseInventory(ReleaseInventoryCommand releaseInventoryCommand) {
    InventoryReservation inventoryReservation =
        inventoryReservationRepository
            .findByOrderId(releaseInventoryCommand.orderId())
            .orElseThrow();

    inventoryReservation.setStatus("RELEASED");
    InventoryReleasedEvent inventoryReleasedEvent =
        new InventoryReleasedEvent(releaseInventoryCommand.orderId());
    inventoryReleaseEventProducer.publishReleaseInventoryEvent(inventoryReleasedEvent);
  }
}
