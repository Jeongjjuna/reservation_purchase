package com.example.ecommerce_service.order.application;

import com.example.ecommerce_service.order.application.port.OrderRepository;
import com.example.ecommerce_service.order.domain.Order;
import com.example.ecommerce_service.order.domain.OrderCreate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    /**
     * 주문 생성
     */
    @Transactional
    public Long create(final OrderCreate orderCreate) {
        final Order order = Order.create(orderCreate);

        final Order saved = orderRepository.save(order);

        return saved.getId();
    }

}
