package com.example.module_order_service.order.presentation.internal;

import com.example.module_order_service.order.application.OrderReadService;
import com.example.module_order_service.order.application.OrderService;
import com.example.module_order_service.order.domain.Order;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/internal/orders")
public class OrderInternalApiController {

    private final OrderService orderService;
    private final OrderReadService orderReadService;

    @GetMapping("/{orderId}")
    public Order findById(@PathVariable("orderId") Long orderId) {
        return orderReadService.find(orderId);
    }

    @DeleteMapping("/{orderId}")
    public Order cancel(
            @PathVariable("orderId") Long orderId
    ) {
        return orderService.cancel(orderId);
    }

    @PostMapping("/{orderId}/complete")
    public Order complete(
            @PathVariable("orderId") Long orderId
    ) {
        return orderService.complete(orderId);
    }
}
