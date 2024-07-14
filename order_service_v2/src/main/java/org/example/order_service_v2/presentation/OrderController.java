package org.example.order_service_v2.presentation;

import lombok.AllArgsConstructor;
import org.example.order_service_v2.application.OrderService;
import org.example.order_service_v2.common.response.Response;
import org.example.order_service_v2.domain.OrderCreate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/v2/orders")
public class OrderController {

    private final OrderService orderService;

    /**
     * 주문 생성(성능 개선 버전)
     */
    @PostMapping
    public Response<Long> create(@RequestBody final OrderCreate orderCreate) {
        orderService.create(orderCreate);
        return Response.success(null);
    }
}
