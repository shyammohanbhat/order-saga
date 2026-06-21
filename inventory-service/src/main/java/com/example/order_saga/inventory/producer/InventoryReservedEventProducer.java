package com.example.order_saga.inventory.producer;

import com.example.order_saga.inventory.dto.InventoryReservedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryReservedEventProducer {
  private final KafkaTemplate<String, String> kafkaTemplate;
  ObjectMapper objectMapper = new ObjectMapper();

  public void publishReserveInventoryEvent(InventoryReservedEvent event) {

    kafkaTemplate.send("inventory-reserved", objectMapper.writeValueAsString(event));

    log.info("Published ReserveInventoryEvent for order {}", event.orderId());
  }
}
