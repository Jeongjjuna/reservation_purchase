package com.example.ecommerce_service.payment.application;

import com.example.ecommerce_service.payment.application.port.PaymentRepository;
import com.example.ecommerce_service.payment.domain.Payment;
import com.example.ecommerce_service.payment.domain.PaymentCreate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public Long create(final PaymentCreate paymentCreate) {
        final Payment payment = Payment.create(paymentCreate);

        final Payment saved = paymentRepository.save(payment);

        return saved.getId();
    }
}
