package com.example.order_saga.orchestrator.dto;

public record ReserveInventoryCommand(
    Long orderId, Long productId, Integer quantity, Double amount) {}
