package com.example.order_saga.orchestrator.dto;

public record InventoryReservedEvent(Long orderId, Long reservationId, Double amount) {}
