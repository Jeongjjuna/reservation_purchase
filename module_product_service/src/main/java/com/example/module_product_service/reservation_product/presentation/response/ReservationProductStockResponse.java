package com.example.module_product_service.reservation_product.presentation.response;

import com.example.module_product_service.reservation_product.domain.ReservationProductStock;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReservationProductStockResponse {

    private Long productId;
    private Integer stockCount;

    @Builder
    public ReservationProductStockResponse(final Long productId, final Integer stockCount) {
        this.productId = productId;
        this.stockCount = stockCount;
    }

    public static ReservationProductStockResponse from(ReservationProductStock reservationProductStock) {
        return ReservationProductStockResponse.builder()
                .productId(reservationProductStock.getProductId())
                .stockCount(reservationProductStock.getStockCount())
                .build();
    }
}
