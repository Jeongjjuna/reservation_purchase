package com.example.ecommerce_service.order.infrastructure;

import com.example.ecommerce_service.order.application.port.OrderRepository;
import com.example.ecommerce_service.order.domain.Order;
import com.example.ecommerce_service.order.infrastructure.entity.OrderEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Order save(final Order orderId) {
        return orderJpaRepository.save(OrderEntity.from(orderId)).toModel();
    }

    @Override
    public Optional<Order> findById(final Long orderId) {
        return orderJpaRepository.findById(orderId).map(OrderEntity::toModel);
    }

    @Override
    public Page<Order> findAll(final Pageable pageable) {
        return orderJpaRepository.findAll(pageable).map(OrderEntity::toModel);
    }
}
