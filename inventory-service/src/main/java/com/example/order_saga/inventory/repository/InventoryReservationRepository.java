package com.example.order_saga.inventory.repository;

import com.example.order_saga.inventory.entity.InventoryReservation;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryReservationRepository extends JpaRepository<InventoryReservation, Long> {
  Optional<InventoryReservation> findByOrderId(Long orderId);
}
