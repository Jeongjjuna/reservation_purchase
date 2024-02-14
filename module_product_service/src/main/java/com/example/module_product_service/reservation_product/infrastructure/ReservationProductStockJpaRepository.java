package com.example.module_product_service.reservation_product.infrastructure;


import com.example.module_product_service.reservation_product.infrastructure.entity.ReservationProductStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationProductStockJpaRepository extends JpaRepository<ReservationProductStockEntity, Long> {
}
