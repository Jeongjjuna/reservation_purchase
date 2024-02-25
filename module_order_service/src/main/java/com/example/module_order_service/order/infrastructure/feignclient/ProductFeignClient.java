package com.example.module_order_service.order.infrastructure.feignclient;

import com.example.module_order_service.order.domain.OrderProduct;
import com.example.module_order_service.order.domain.ReservationProductStock;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "productFeignClient", url = "${feign.productClient.url}")
public interface ProductFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/v1/internal/reservation-products/{reservationProductId}")
    OrderProduct findByReservationProductId(@PathVariable("reservationProductId") Long reservationProductId);

    @RequestMapping(method = RequestMethod.GET, value = "/v1/internal/reservation-product-stock/{reservationProductId}")
    ReservationProductStock findByReservationProductStockId(@PathVariable("reservationProductId") Long reservationProductId);

    @RequestMapping(method = RequestMethod.PUT,value = "/v1/internal/reservation-product-stock/{reservationProductId}", consumes = "application/json")
    ReservationProductStock updateReservationProductStock(
            @PathVariable("reservationProductId") Long reservationProductId,
            ReservationProductStock reservationProductStock
    );

    @RequestMapping(method = RequestMethod.GET, value = "/v1/internal/reservation-time/products/{productId}")
    Boolean isAfterReservationStartAt(@PathVariable Long productId);
}
