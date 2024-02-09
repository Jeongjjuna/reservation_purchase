package com.example.ecommerce_service.order.presentation;

import com.example.ecommerce_service.common.response.Response;
import com.example.ecommerce_service.order.application.OrderService;
import com.example.ecommerce_service.order.domain.OrderCreate;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/orders")
public class OrderApiController {

    private final OrderService orderService;

    @PostMapping
    public Response<Void> create(@RequestBody final OrderCreate orderCreate) {
        orderService.create(orderCreate);
        return Response.success();
    }

}
