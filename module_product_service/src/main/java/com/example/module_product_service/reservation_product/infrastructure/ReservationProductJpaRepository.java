package com.example.module_product_service.reservation_product.infrastructure;

import com.example.module_product_service.reservation_product.infrastructure.entity.ReservationProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationProductJpaRepository extends JpaRepository<ReservationProductEntity, Long> {
}
