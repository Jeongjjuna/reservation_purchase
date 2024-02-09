package com.example.ecommerce_service.reservation_product.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReservationProductStock {

    private Long productId;
    private Integer stockCount;

    @Builder
    public ReservationProductStock(final Long productId, final Integer stockCount) {
        this.productId = productId;
        this.stockCount = stockCount;
    }

    public static ReservationProductStock create(
            final Long productId,
            final ReservationProductCreate reservationProductCreate
    ) {
        return ReservationProductStock.builder()
                .productId(productId)
                .stockCount(reservationProductCreate.getStockCount())
                .build();
    }

    public ReservationProductStock update(final ReservationProductUpdate reservationProductUpdate) {
        this.stockCount = reservationProductUpdate.getStockCount();
        return this;
    }
}