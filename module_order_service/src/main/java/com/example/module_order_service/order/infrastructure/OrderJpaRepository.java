package com.example.module_order_service.order.infrastructure;


import com.example.module_order_service.order.infrastructure.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {
}
