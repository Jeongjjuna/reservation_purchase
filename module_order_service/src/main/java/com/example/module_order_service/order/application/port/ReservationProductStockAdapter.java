package com.example.module_order_service.order.application.port;


import com.example.module_order_service.order.domain.ReservationProductStock;
import java.util.Optional;

public interface ReservationProductStockAdapter {

    /**
     * 상품id를 통해 상품 재고 정보를 요청한다.
     */
    Optional<ReservationProductStock> findById(Long productId);

    /**
     * 상품 재고 정보를 통해서 재고정보 업데이트를 요청한다.
     */
    ReservationProductStock update(final ReservationProductStock reservationProductStock);
}
