package com.example.order_saga.payment.dto;

public record ProcessPaymentCommand(Long orderId, Double amount) {}
