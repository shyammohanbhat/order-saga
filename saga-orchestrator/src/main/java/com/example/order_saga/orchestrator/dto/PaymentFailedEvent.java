package com.example.order_saga.orchestrator.dto;

public record PaymentFailedEvent(Long orderId, Double amount, String reason) {}
