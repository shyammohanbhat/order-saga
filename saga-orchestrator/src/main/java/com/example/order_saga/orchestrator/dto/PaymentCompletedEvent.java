package com.example.order_saga.orchestrator.dto;

public record PaymentCompletedEvent(Long orderId, Long paymentId, Double amount) {}
