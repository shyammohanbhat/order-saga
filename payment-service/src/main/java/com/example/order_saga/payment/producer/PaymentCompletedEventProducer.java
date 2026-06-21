package com.example.order_saga.payment.producer;

import com.example.order_saga.payment.dto.PaymentCompletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentCompletedEventProducer {
  private final KafkaTemplate<String, String> kafkaTemplate;
  ObjectMapper objectMapper = new ObjectMapper();

  public void publishPaymentSuccessEvent(PaymentCompletedEvent event) {

    kafkaTemplate.send("payment-succeeded", objectMapper.writeValueAsString(event));

    log.info("Published PaymentCompletedEvent for order {}", event.orderId());
  }
}
