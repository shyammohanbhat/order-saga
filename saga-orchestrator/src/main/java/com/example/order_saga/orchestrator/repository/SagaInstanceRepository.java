package com.example.order_saga.orchestrator.repository;

import com.example.order_saga.orchestrator.entity.SagaInstance;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SagaInstanceRepository extends JpaRepository<SagaInstance, Long> {
  Optional<SagaInstance> findByOrderId(Long orderId);
}
