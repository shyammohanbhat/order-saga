package com.example.order_saga.payment.repository;

import com.example.order_saga.payment.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentsRepository extends JpaRepository<Payments, Long> {}
