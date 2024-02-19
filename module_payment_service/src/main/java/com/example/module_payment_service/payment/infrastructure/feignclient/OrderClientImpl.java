package com.example.module_payment_service.payment.infrastructure.feignclient;

import com.example.module_payment_service.payment.application.port.OrderAdapter;
import com.example.module_payment_service.payment.domain.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class OrderClientImpl implements OrderAdapter {

    private final OrderFeignClient orderFeignClient;

    @Override
    public Optional<Order> findById(final Long orderId) {
        return Optional.ofNullable(orderFeignClient.findById(orderId));
    }

    @Override
    public Order cancel(final Long orderId) {
        return orderFeignClient.cancel(orderId);
    }
}
