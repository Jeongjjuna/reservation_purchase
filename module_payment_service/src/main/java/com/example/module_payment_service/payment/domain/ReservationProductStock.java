package com.example.module_payment_service.payment.domain;

import lombok.Getter;

@Getter
public class ReservationProductStock {

    private Long productId;
    private Integer stockCount;

    public ReservationProductStock(final Long productId, final Integer stockCount) {
        this.productId = productId;
        this.stockCount = stockCount;
    }
}
