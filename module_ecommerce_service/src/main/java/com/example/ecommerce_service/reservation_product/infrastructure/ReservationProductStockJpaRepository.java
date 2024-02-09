package com.example.ecommerce_service.reservation_product.infrastructure;

import com.example.ecommerce_service.reservation_product.infrastructure.entity.ReservationProductStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationProductStockJpaRepository extends JpaRepository<ReservationProductStockEntity, Long> {
}