package com.example.order_saga.shipment.consumer;

import com.example.order_saga.shipment.dto.ProcessShipmentCommand;
import com.example.order_saga.shipment.service.ShipmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProcessShipmentConsumer {

  private final ShipmentService shipmentService;
  ObjectMapper objectMapper = new ObjectMapper();

  @KafkaListener(topics = "create-shipment-command", groupId = "shipment-service")
  public void consume(String event) {
    log.info("Received ProcessShipmentCommand : {}", event);

    shipmentService.processShipment(objectMapper.readValue(event, ProcessShipmentCommand.class));
  }
}
