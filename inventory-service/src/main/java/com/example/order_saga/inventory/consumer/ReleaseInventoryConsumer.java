package com.example.order_saga.inventory.consumer;

import com.example.order_saga.inventory.dto.ReleaseInventoryCommand;
import com.example.order_saga.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReleaseInventoryConsumer {

  private final InventoryService inventoryService;
  ObjectMapper objectMapper = new ObjectMapper();

  @KafkaListener(topics = "release-inventory-command", groupId = "inventory-service")
  public void consume(String event) {
    log.info("Received ReleaseInventoryCommand : {}", event);

    inventoryService.releaseInventory(
        objectMapper.readValue(event, ReleaseInventoryCommand.class));
  }
}
