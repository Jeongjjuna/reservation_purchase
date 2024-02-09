package com.example.ecommerce_service.payment.application.port;

import com.example.ecommerce_service.payment.domain.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface PaymentRepository {

    Payment save(Payment payment);

    Optional<Payment> findById(Long paymentId);

    Page<Payment> findAll(Pageable pageable);
}
