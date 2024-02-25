package com.example.module_product_service.product.infrastructure.repository;

import com.example.module_product_service.product.infrastructure.repository.entity.ReservationTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ReservationTimeJpaRepository extends JpaRepository<ReservationTimeEntity, Long> {
    Optional<ReservationTimeEntity> findByProductId(Long productId);
}
