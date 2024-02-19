package com.example.module_product_service.product.infrastructure.repository;

import com.example.module_product_service.product.infrastructure.repository.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationProductJpaRepository extends JpaRepository<ProductEntity, Long> {
}
