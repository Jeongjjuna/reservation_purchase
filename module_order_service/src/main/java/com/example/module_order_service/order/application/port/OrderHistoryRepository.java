package com.example.module_order_service.order.application.port;

import com.example.module_order_service.order.domain.OrderHistory;
import java.util.Optional;

public interface OrderHistoryRepository {

    OrderHistory save(OrderHistory orderHistory);

    Optional<OrderHistory> findByOrderId(Long orderId);

}
