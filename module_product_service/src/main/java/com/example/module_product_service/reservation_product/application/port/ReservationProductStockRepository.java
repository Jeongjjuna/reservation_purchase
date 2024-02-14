package com.example.module_product_service.reservation_product.application.port;


import com.example.module_product_service.reservation_product.domain.ReservationProductStock;
import java.util.Optional;

public interface ReservationProductStockRepository {
    ReservationProductStock save(ReservationProductStock reservationProductStock);

    Optional<ReservationProductStock> findById(Long productId);
}
