package com.example.module_payment_service.payment.infrastructure.feignclient;

import com.example.module_payment_service.payment.domain.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "orderFeignClient", url = "${feign.orderClient.url}")
public interface OrderFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/v1/internal/orders/{orderId}")
    Order findById(@PathVariable("orderId") Long orderId);

    @RequestMapping(method = RequestMethod.DELETE, value = "/v1/internal/orders/{orderId}", consumes = "application/json")
    Order cancel(@PathVariable final Long orderId);

    @RequestMapping(method = RequestMethod.POST, value = "/v1/internal/orders/{orderId}/complete", consumes = "application/json")
    Order complete(@PathVariable final Long orderId);
}
