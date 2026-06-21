package com.example.order_saga.orchestrator.repository;

import com.example.order_saga.orchestrator.entity.SagaEvents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SagaEventsRepository extends JpaRepository<SagaEvents, Long> {}
