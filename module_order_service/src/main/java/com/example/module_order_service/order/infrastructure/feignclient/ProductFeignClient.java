package com.example.module_order_service.order.infrastructure.feignclient;

import com.example.module_order_service.order.domain.ReservationProductStock;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "productFeignClient", url = "${feign.productClient.url}")
public interface ProductFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/v1/internal/reservation-products/{reservationProductId}")
    ReservationProductStock findByReservationProductStockId(@PathVariable("reservationProductId") Long reservationProductId);

    @RequestMapping(method = RequestMethod.PUT,value = "/v1/internal/reservation-products/{reservationProductId}", consumes = "application/json")
    ReservationProductStock updateReservationProductStock(
            @PathVariable("reservationProductId") Long reservationProductId,
            ReservationProductStock reservationProductStock
    );
}
