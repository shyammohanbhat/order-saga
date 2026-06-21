package com.example.order_saga.payment.service;

import com.example.order_saga.payment.dto.PaymentCompletedEvent;
import com.example.order_saga.payment.dto.PaymentFailedEvent;
import com.example.order_saga.payment.dto.ProcessPaymentCommand;
import com.example.order_saga.payment.entity.Payments;
import com.example.order_saga.payment.producer.PaymentCompletedEventProducer;
import com.example.order_saga.payment.producer.PaymentFailedEventProducer;
import com.example.order_saga.payment.repository.PaymentsRepository;
import java.time.LocalDateTime;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
  private final PaymentsRepository paymentsRepository;
  private final PaymentCompletedEventProducer paymentCompletedEventProducer;
  private final PaymentFailedEventProducer paymentFailedEventProducer;

  public void processPayment(ProcessPaymentCommand processPaymentCommand) {
    Random r = new Random();

    // Adding random logic for payment failure demonstration
    boolean paymentSuccess = r.nextInt() % 2 == 0;

    Payments payments =
        Payments.builder()
            .orderId(processPaymentCommand.orderId())
            .amount(processPaymentCommand.amount())
            .status(paymentSuccess ? "SUCCESS" : "FAILED")
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    Long paymentId = paymentsRepository.save(payments).getPaymentId();

    if (paymentSuccess) {
      PaymentCompletedEvent paymentCompletedEvent =
          new PaymentCompletedEvent(
              processPaymentCommand.orderId(), paymentId, processPaymentCommand.amount());
      paymentCompletedEventProducer.publishPaymentSuccessEvent(paymentCompletedEvent);
    } else {
      PaymentFailedEvent paymentFailedEvent =
          new PaymentFailedEvent(
              processPaymentCommand.orderId(),
              processPaymentCommand.amount(),
              "Insufficient Balance");
      paymentFailedEventProducer.publishPaymentFailedEvent(paymentFailedEvent);
    }
  }
}
