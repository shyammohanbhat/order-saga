package com.example.order_saga.inventory.service;

import com.example.order_saga.inventory.dto.InventoryReservedEvent;
import com.example.order_saga.inventory.dto.ReserveInventoryCommand;
import com.example.order_saga.inventory.entity.InventoryReservation;
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
}
