package com.example.order_saga.order.service;

import com.example.order_saga.order.dto.OrderCreatedEvent;
import com.example.order_saga.order.dto.OrderRequest;
import com.example.order_saga.order.dto.OrderResponse;
import com.example.order_saga.order.entity.Orders;
import com.example.order_saga.order.producer.OrderEventProducer;
import com.example.order_saga.order.repository.OrderRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final OrderEventProducer orderEventProducer;

  public OrderResponse createOrder(OrderRequest orderRequest) {
    Orders orders =
        Orders.builder()
            .customerId(orderRequest.getCustomerId())
            .amount(orderRequest.getAmount())
            .status(orderRequest.getStatus())
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    Long orderId = orderRepository.save(orders).getId();

    OrderCreatedEvent event =
        OrderCreatedEvent.builder()
            .orderId(orderId)
            .amount(orderRequest.getAmount())
            .customerId(orderRequest.getCustomerId())
            .build();

    ObjectMapper objectMapper = new ObjectMapper();

    orderEventProducer.publishOrderCreated(objectMapper.writeValueAsString(event));
    return OrderResponse.builder().orderId(orderId).build();
  }
}
