package com.example.module_order_service.order.application.port;

import com.example.module_order_service.order.domain.OrderHistory;

public interface OrderHistoryRepository {

    OrderHistory save(OrderHistory orderHistory);
}
