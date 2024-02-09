package com.example.ecommerce_service.payment.infrastructure;

import com.example.ecommerce_service.payment.application.port.PaymentRepository;
import com.example.ecommerce_service.payment.domain.Payment;
import com.example.ecommerce_service.payment.infrastructure.entity.PaymentEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public Payment save(final Payment payment) {
        return paymentJpaRepository.save(PaymentEntity.from(payment)).toModel();
    }

    @Override
    public Optional<Payment> findById(final Long paymentId) {
        return paymentJpaRepository.findById(paymentId).map(PaymentEntity::toModel);
    }

    @Override
    public Page<Payment> findAll(final Pageable pageable) {
        return paymentJpaRepository.findAll(pageable).map(PaymentEntity::toModel);
    }
}
