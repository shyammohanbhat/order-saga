package com.example.order_saga.orchestrator.dto;

public record ShipmentCreatedEvent(Long orderId, String trackingId, Long shipmentId) {}
