package com.example.module_order_service.order.infrastructure.client;

import com.example.module_order_service.order.application.port.ReservationProductStockRepository;
import com.example.module_order_service.order.domain.ReservationProductStock;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@Repository
public class ProductClientImpl implements ReservationProductStockRepository {

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
