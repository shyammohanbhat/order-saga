package com.example.order_saga.shipment.dto;

public record ShipmentCreatedEvent(Long orderId, String trackingId, Long shipmentId) {}
