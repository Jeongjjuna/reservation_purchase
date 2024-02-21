package com.example.module_order_service.order.infrastructure.feignclient;

import com.example.module_order_service.order.domain.OrderStock;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "stockFeignClient", url = "${feign.stockClient.url}")
public interface StockFeignClient {
    @RequestMapping(method = RequestMethod.POST, value = "/v1/internal/stock/increase/products/{productId}")
    void addStock(
            @PathVariable("productId") final Long productId,
            @RequestBody final OrderStock stockCount
    );

    @RequestMapping(method = RequestMethod.POST, value = "/v1/internal/stock/decrease/products/{productId}")
    void subtractStock(
            @PathVariable("productId") final Long productId,
            @RequestBody final OrderStock stockCount
    );
}
