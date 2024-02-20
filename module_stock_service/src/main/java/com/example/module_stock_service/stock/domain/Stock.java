package com.example.module_stock_service.stock.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Stock {

    private Long productId;
    private Integer stockCount;

    @Builder
    public Stock(final Long productId, final Integer stockCount) {
        this.productId = productId;
        this.stockCount = stockCount;
    }

    public static Stock create(
            final Long productId,
            final Integer stockCount
    ) {
        return Stock.builder()
                .productId(productId)
                .stockCount(stockCount)
                .build();
    }

    public Stock update(final Integer stockCount) {
        this.stockCount = stockCount;
        return this;
    }

    public Stock validateStock() {
        if (stockCount  <= 0) {
            throw new IllegalArgumentException("재고 개수가 0보다 같거나 작습니다.");
        }
        return this;
    }

    public Stock subtractStock(int quantity) {
        stockCount = stockCount - quantity;
        return this;
    }

    public Stock addStock(int quantity) {
        stockCount = stockCount + quantity;
        return this;
    }
}
