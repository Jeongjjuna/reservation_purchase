package com.example.module_payment_service.payment.infrastructure.feignclient;

import com.example.module_payment_service.payment.domain.OrderStock;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "stockFeignClient", url = "${feign.stockClient.url}")
public interface StockFeignClient {
    @RequestMapping(method = RequestMethod.POST, value = "/v1/internal/stock/increase/products/{productId}")
    void addStock(@PathVariable("productId") Long productId, OrderStock stockCount);

    @RequestMapping(method = RequestMethod.POST, value = "/v1/internal/stock/decrease/products/{productId}")
    void subtract(@PathVariable("productId") Long productId, OrderStock stockCount);
}
