package com.example.module_order_service.order.domain;

import lombok.Getter;

@Getter
public class ReservationProductStock {

    private Long productId;
    private Integer stockCount;

    public ReservationProductStock(final Long productId, final Integer stockCount) {
        this.productId = productId;
        this.stockCount = stockCount;
    }

    public ReservationProductStock validateStock() {
        if (stockCount  <= 0) {
            throw new IllegalArgumentException("재고 개수가 0보다 같거나 작습니다.");
        }
        return this;
    }

    public ReservationProductStock subtractStockByOne() {
        stockCount = stockCount - 1;
        return this;
    }

    public ReservationProductStock addStockByOne() {
        stockCount = stockCount + 1;
        return this;
    }
}
