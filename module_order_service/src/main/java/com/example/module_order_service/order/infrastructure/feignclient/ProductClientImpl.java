package com.example.module_order_service.order.infrastructure.feignclient;

import com.example.module_order_service.order.application.port.ReservationProductStockAdapter;
import com.example.module_order_service.order.domain.OrderProduct;
import com.example.module_order_service.order.domain.ReservationProductStock;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class ProductClientImpl implements ReservationProductStockAdapter {

    private final ProductFeignClient productFeignClient;

    @Override
    public Optional<OrderProduct> findOrderProductById(final Long productId) {
        return Optional.ofNullable(productFeignClient.findByReservationProductId(productId));
    }

    @Override
    public Optional<ReservationProductStock> findById(final Long productId) {
        return Optional.ofNullable(productFeignClient.findByReservationProductStockId(productId));
    }

    @Override
    public ReservationProductStock update(final ReservationProductStock reservationProductStock) {
        return productFeignClient.updateReservationProductStock(reservationProductStock.getProductId(), reservationProductStock);
    }

}
