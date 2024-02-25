package com.example.module_order_service.order.infrastructure.feignclient;

import com.example.module_order_service.order.application.port.ProductServiceAdapter;
import com.example.module_order_service.order.domain.OrderProduct;
import com.example.module_order_service.order.domain.ReservationProductStock;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ProductClientImpl implements ProductServiceAdapter {

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

    @Override
    public Boolean isAfterReservationStartAt(final Long productId) {
        return productFeignClient.isAfterReservationStartAt(productId);
    }

}
