package com.example.module_order_service.order.infrastructure.repository;

import com.example.module_order_service.order.application.port.OrderHistoryRepository;
import com.example.module_order_service.order.domain.OrderHistory;
import com.example.module_order_service.order.infrastructure.repository.entity.OrderHistoryEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class OrderHistoryRepositoryImpl implements OrderHistoryRepository {

    private final OrderHistoryJpaRepository orderHistoryJpaRepository;

    @Override
    public OrderHistory save(final OrderHistory orderHistory) {
        return orderHistoryJpaRepository.save(OrderHistoryEntity.from(orderHistory)).toModel();
    }

    @Override
    public Optional<OrderHistory> findByOrderId(final Long orderId) {
        return orderHistoryJpaRepository.findById(orderId).map(OrderHistoryEntity::toModel);
    }

}
