package com.example.order_saga.inventory.dto;

public record ReserveInventoryCommand(Long orderId, Long productId, Integer quantity) {}
