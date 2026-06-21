package com.example.order_saga.shipment.producer;

import com.example.order_saga.shipment.dto.ShipmentCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShipmentCreatedEventProducer {
  private final KafkaTemplate<String, String> kafkaTemplate;
  ObjectMapper objectMapper = new ObjectMapper();

  public void publishShipmentCreatedEvent(ShipmentCreatedEvent event) {

    kafkaTemplate.send("shipment-created", objectMapper.writeValueAsString(event));

    log.info("Published ShipmentCreatedEvent for order {}", event.orderId());
  }
}
