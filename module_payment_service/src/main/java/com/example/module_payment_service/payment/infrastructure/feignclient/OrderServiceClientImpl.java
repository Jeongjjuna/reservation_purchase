package com.example.module_payment_service.payment.infrastructure.feignclient;

import com.example.module_payment_service.payment.application.port.OrderServiceAdapter;
import com.example.module_payment_service.payment.domain.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class OrderServiceClientImpl implements OrderServiceAdapter {

    private final OrderFeignClient orderFeignClient;

    @Override
    public Optional<Order> findById(final Long orderId) {
        return Optional.ofNullable(orderFeignClient.findById(orderId));
    }

    @Override
    public Order cancel(final Long orderId) {
        return orderFeignClient.cancel(orderId);
    }

    @Override
    public Order complete(final Long orderId) {
        return orderFeignClient.complete(orderId);
    }
}
