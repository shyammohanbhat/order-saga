package com.example.order_saga.inventory.producer;

import com.example.order_saga.inventory.dto.InventoryReleasedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryReleaseEventProducer {
  private final KafkaTemplate<String, String> kafkaTemplate;
  ObjectMapper objectMapper = new ObjectMapper();

  public void publishReleaseInventoryEvent(InventoryReleasedEvent event) {

    kafkaTemplate.send("inventory-released", objectMapper.writeValueAsString(event));

    log.info("Published ReleaseInventoryEvent for order {}", event.orderId());
  }
}
