package com.example.order_saga.shipment.service;

import com.example.order_saga.shipment.dto.ProcessShipmentCommand;
import com.example.order_saga.shipment.dto.ShipmentCreatedEvent;
import com.example.order_saga.shipment.entity.Shipments;
import com.example.order_saga.shipment.producer.ShipmentCreatedEventProducer;
import com.example.order_saga.shipment.repository.ShipmentsRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShipmentService {
  private final ShipmentsRepository shipmentsRepository;
  private final ShipmentCreatedEventProducer shipmentCreatedEventProducer;

  public void processShipment(ProcessShipmentCommand processShipmentCommand) {
    UUID trackingId = java.util.UUID.randomUUID();
    Shipments shipments =
        Shipments.builder()
            .orderId(processShipmentCommand.orderId())
            .trackingId(trackingId.toString())
            .status("CREATED")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    Long shipmentId = shipmentsRepository.save(shipments).getShipmentId();

    ShipmentCreatedEvent shipmentCreatedEvent =
        new ShipmentCreatedEvent(
            processShipmentCommand.orderId(), trackingId.toString(), shipmentId);
    shipmentCreatedEventProducer.publishShipmentCreatedEvent(shipmentCreatedEvent);
  }
}
