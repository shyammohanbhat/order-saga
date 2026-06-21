package com.example.order_saga.payment.producer;

import com.example.order_saga.payment.dto.PaymentFailedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentFailedEventProducer {
  private final KafkaTemplate<String, String> kafkaTemplate;
  ObjectMapper objectMapper = new ObjectMapper();

  public void publishPaymentFailedEvent(PaymentFailedEvent event) {

    kafkaTemplate.send("payment-failed", objectMapper.writeValueAsString(event));

    log.info("Published PaymentFailedEvent for order {}", event.orderId());
  }
}
