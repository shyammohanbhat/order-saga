package com.example.order_saga.payment.service;

import com.example.order_saga.payment.dto.PaymentCompletedEvent;
import com.example.order_saga.payment.dto.ProcessPaymentCommand;
import com.example.order_saga.payment.entity.Payments;
import com.example.order_saga.payment.producer.PaymentCompletedEventProducer;
import com.example.order_saga.payment.repository.PaymentsRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
  private final PaymentsRepository paymentsRepository;
  private final PaymentCompletedEventProducer paymentCompletedEventProducer;

  public void processPayment(ProcessPaymentCommand processPaymentCommand) {
    Payments payments =
        Payments.builder()
            .orderId(processPaymentCommand.orderId())
            .amount(processPaymentCommand.amount())
            .status("SUCCESS")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    Long paymentId = paymentsRepository.save(payments).getPaymentId();

    PaymentCompletedEvent paymentCompletedEvent =
        new PaymentCompletedEvent(
            processPaymentCommand.orderId(), paymentId, processPaymentCommand.amount());
    paymentCompletedEventProducer.publishPaymentSuccessEvent(paymentCompletedEvent);
  }
}
