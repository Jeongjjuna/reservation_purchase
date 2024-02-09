package com.example.ecommerce_service.product.infrastructure;

import com.example.ecommerce_service.product.infrastructure.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {
}