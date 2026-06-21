package com.example.order_saga.orchestrator.producer;

import com.example.order_saga.orchestrator.dto.ReserveInventoryCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
@Slf4j
public class SagaCommandProducer {
  private final KafkaTemplate<String, String> kafkaTemplate;
  ObjectMapper objectMapper = new ObjectMapper();

  public void publishReserveInventory(ReserveInventoryCommand command) {

    kafkaTemplate.send("reserve-inventory-command", objectMapper.writeValueAsString(command));

    log.info("Published ReserveInventoryCommand for order {}", command.orderId());
  }
}
