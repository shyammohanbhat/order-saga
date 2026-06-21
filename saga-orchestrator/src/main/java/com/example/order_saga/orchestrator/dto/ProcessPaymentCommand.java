package com.example.order_saga.orchestrator.dto;

public record ProcessPaymentCommand(Long orderId, Double amount) {}
