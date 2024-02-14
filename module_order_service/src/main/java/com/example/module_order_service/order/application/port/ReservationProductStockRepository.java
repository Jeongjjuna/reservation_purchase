package com.example.module_order_service.order.application.port;


import com.example.module_order_service.order.domain.ReservationProductStock;
import java.util.Optional;

public interface ReservationProductStockRepository {

    Optional<ReservationProductStock> findById(Long productId);

    ReservationProductStock update(final ReservationProductStock reservationProductStock);
}
