package com.example.ecommerce_service.product.infrastructure;

import com.example.ecommerce_service.product.infrastructure.entity.ProductStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductStockJpaRepository extends JpaRepository<ProductStockEntity, Long> {
}