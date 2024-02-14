package com.example.module_product_service.reservation_product.infrastructure.repository;


import com.example.module_product_service.reservation_product.infrastructure.repository.entity.ReservationProductStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationProductStockJpaRepository extends JpaRepository<ReservationProductStockEntity, Long> {
}
