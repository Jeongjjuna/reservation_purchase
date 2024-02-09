package com.example.ecommerce_service.order.application;

import com.example.ecommerce_service.common.exception.GlobalException;
import com.example.ecommerce_service.order.application.port.OrderRepository;
import com.example.ecommerce_service.order.domain.Order;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class OrderReadService {

    private final OrderRepository orderRepository;

    public Order find(final Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() ->
                new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] order not found"));
    }
}
