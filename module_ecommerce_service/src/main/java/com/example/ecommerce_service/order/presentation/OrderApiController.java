package com.example.ecommerce_service.order.presentation;

import com.example.ecommerce_service.common.response.Response;
import com.example.ecommerce_service.order.application.OrderReadService;
import com.example.ecommerce_service.order.application.OrderService;
import com.example.ecommerce_service.order.domain.Order;
import com.example.ecommerce_service.order.domain.OrderCreate;
import com.example.ecommerce_service.order.presentation.response.OrderResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/orders")
public class OrderApiController {

    private final OrderService orderService;
    private final OrderReadService orderReadService;

    /**
     * 주문 생성(결제 페이지 진입 시 요청)
     */
    @PostMapping
    public Response<Long> create(@RequestBody final OrderCreate orderCreate) {
        final Long savedOrderId = orderService.create(orderCreate);
        return Response.success(savedOrderId);
    }

    /**
     * 주문 취소(결제 페이지 진입 후에, 고객 변심 발생 시 요청)
     */
    @DeleteMapping("/{id}")
    public Response<Void> cancel(@PathVariable Long id) {
        orderService.cancel(id);
        return Response.success();
    }

    /**
     * 주문 단건 조회
     */
    @GetMapping("/{id}")
    public Response<OrderResponse> read(@PathVariable Long id) {
        Order order = orderReadService.find(id);
        return Response.success(OrderResponse.from(order));
    }

}
