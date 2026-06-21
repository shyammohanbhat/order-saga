package com.example.order_saga.inventory.consumer;

import com.example.order_saga.inventory.dto.ReserveInventoryCommand;
import com.example.order_saga.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReserveInventoryConsumer {

  private final InventoryService inventoryService;
  ObjectMapper objectMapper = new ObjectMapper();

  @KafkaListener(topics = "reserve-inventory-command", groupId = "inventory-service")
  public void consume(String event) {
    log.info("Received ReserveInventoryCommand : {}", event);

    inventoryService.saveInventoryReservation(
        objectMapper.readValue(event, ReserveInventoryCommand.class));
  }
}
