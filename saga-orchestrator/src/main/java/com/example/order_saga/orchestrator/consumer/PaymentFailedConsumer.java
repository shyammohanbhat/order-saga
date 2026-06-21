package com.example.order_saga.orchestrator.consumer;

import com.example.order_saga.orchestrator.dto.PaymentFailedEvent;
import com.example.order_saga.orchestrator.service.SagaCoordinatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentFailedConsumer {
  private final SagaCoordinatorService sagaCoordinatorService;
  ObjectMapper objectMapper = new ObjectMapper();

  @KafkaListener(topics = "payment-failed", groupId = "saga-orchestrator")
  public void consume(String event) {
    log.info("Received PaymentFail : {}", event);

    sagaCoordinatorService.handlePaymentFailed(
        objectMapper.readValue(event, PaymentFailedEvent.class));
  }
}
