package com.example.order_saga.payment.consumer;

import com.example.order_saga.payment.dto.ProcessPaymentCommand;
import com.example.order_saga.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProcessPaymentConsumer {

  private final PaymentService paymentService;
  ObjectMapper objectMapper = new ObjectMapper();

  @KafkaListener(topics = "process-payment-command", groupId = "payment-service")
  public void consume(String event) {
    log.info("Received ProcessPaymentCommand : {}", event);

    paymentService.processPayment(objectMapper.readValue(event, ProcessPaymentCommand.class));
  }
}
