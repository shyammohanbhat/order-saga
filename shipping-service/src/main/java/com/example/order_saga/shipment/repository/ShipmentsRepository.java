package com.example.order_saga.shipment.repository;

import com.example.order_saga.shipment.entity.Shipments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentsRepository extends JpaRepository<Shipments, Long> {}
