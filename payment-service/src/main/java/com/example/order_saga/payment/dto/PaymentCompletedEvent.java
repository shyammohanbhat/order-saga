package com.example.order_saga.payment.dto;

public record PaymentCompletedEvent(Long orderId, Long paymentId, Double amount) {}
