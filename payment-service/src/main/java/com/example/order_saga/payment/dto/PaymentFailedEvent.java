package com.example.order_saga.payment.dto;

public record PaymentFailedEvent(Long orderId, Double amount, String reason) {}
