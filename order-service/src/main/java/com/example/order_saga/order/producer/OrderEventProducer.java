package com.example.order_saga.order.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventProducer {
  private final KafkaTemplate<String, String> kafkaTemplate;

  public void publishOrderCreated(String event) {
    kafkaTemplate.send("order-created", event);
  }
}
