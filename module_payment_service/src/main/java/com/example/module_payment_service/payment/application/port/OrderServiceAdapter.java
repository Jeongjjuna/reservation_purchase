package com.example.module_payment_service.payment.application.port;

import com.example.module_payment_service.payment.domain.Order;
import java.util.Optional;

public interface OrderServiceAdapter {

    /**
     * 주문id를 통해 주문정보를 요청한다.
     */
    Optional<Order> findById(Long orderId);

    /**
     * 주문id를 통해 주문취소를 요청한다.
     */
    Order cancel(Long orderId);

    Order complete(Long orderId);
}
