package com.example.order_saga.orchestrator.dto;

public record OrderCreatedEvent(
    Long orderId, Double amount, String customerId, Long productId, Integer quantity) {}
