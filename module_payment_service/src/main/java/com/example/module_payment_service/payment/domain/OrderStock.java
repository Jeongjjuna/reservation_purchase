package com.example.module_payment_service.payment.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderStock {

    private Long productId;
    private Integer stockCount;

    @Builder
    public OrderStock(final Long productId, final Integer stockCount) {
        this.productId = productId;
        this.stockCount = stockCount;
    }
}
