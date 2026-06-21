package com.example.order_saga.shipment.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "shipments")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Shipments {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "shipment_id")
  private Long shipmentId;

  @Column(name = "order_id")
  private Long orderId;

  @Column(name = "tracking_number")
  private String trackingId;

  @Column(name = "status")
  private String status;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
}
