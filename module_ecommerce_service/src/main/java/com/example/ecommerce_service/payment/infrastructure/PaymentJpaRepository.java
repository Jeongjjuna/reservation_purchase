package com.example.ecommerce_service.payment.infrastructure;

import com.example.ecommerce_service.payment.infrastructure.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, Long> {
}
