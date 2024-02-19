package com.example.module_order_service.order.infrastructure.repository;

import com.example.module_order_service.order.infrastructure.repository.entity.OrderHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHistoryJpaRepository extends JpaRepository<OrderHistoryEntity, Long> {
}
