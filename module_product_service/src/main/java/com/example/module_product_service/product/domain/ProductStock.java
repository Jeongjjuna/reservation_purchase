package com.example.module_product_service.product.domain;

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

    public static ProductStock create(
            final Long productId,
            final ProductCreate productCreate
    ) {
        return ProductStock.builder()
                .productId(productId)
                .stockCount(productCreate.getStockCount())
                .build();
    }

    public ProductStock update(final Integer stockCount) {
        this.stockCount = stockCount;
        return this;
    }

    public ProductStock validateStock() {
        if (stockCount  <= 0) {
            throw new IllegalArgumentException("재고 개수가 0보다 같거나 작습니다.");
        }
        return this;
    }

    public ProductStock subtractStockByOne() {
        stockCount = stockCount - 1;
        return this;
    }

    public ProductStock addStockByOne() {
        stockCount = stockCount + 1;
        return this;
    }
}
