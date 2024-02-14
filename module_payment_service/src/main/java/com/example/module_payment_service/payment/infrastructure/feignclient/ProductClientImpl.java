package com.example.module_payment_service.payment.infrastructure.feignclient;

import com.example.module_payment_service.payment.application.port.ReservationProductStockAdapter;
import com.example.module_payment_service.payment.domain.ReservationProductStock;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class ProductClientImpl implements ReservationProductStockAdapter {

    private final ProductFeignClient productFeignClient;

    @Override
    public Optional<ReservationProductStock> findById(final Long productId) {
        return Optional.ofNullable(productFeignClient.findByReservationProductStockId(productId));
    }

    @Override
    public ReservationProductStock update(final ReservationProductStock reservationProductStock) {
        return productFeignClient.updateReservationProductStock(reservationProductStock.getProductId(), reservationProductStock);
    }

}
