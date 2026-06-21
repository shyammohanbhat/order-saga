package com.example.order_saga.inventory.dto;

public record InventoryReservedEvent(Long orderId, Long reservationId) {}
