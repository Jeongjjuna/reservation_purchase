package com.example.ecommerce_service.product.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductStock {

    private Long productId;
    private Integer stockCount;

    @Builder
    public ProductStock(final Long productId, final Integer stockCount) {
        this.productId = productId;
        this.stockCount = stockCount;
    }
}
