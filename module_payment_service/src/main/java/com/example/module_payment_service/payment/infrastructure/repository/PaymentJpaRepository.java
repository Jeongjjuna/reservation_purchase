package com.example.module_payment_service.payment.infrastructure.repository;

import com.example.module_payment_service.payment.infrastructure.repository.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, Long> {
}
