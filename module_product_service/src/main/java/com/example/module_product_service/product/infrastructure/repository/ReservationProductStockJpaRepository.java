package com.example.module_product_service.product.infrastructure.repository;


import com.example.module_product_service.product.infrastructure.repository.entity.ProductStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationProductStockJpaRepository extends JpaRepository<ProductStockEntity, Long> {
}
