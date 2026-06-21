package com.example.order_saga.orchestrator.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Table(name = "saga_instance")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SagaInstance {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "saga_id")
  private Long sagaId;

  @Column(name = "order_id")
  private Long orderId;

  @Column(name = "current_step")
  private String currentStep;

  @Column(name = "status")
  private String status;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;
}
