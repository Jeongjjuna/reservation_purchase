package com.example.module_stock_service.stock.domain;

import com.example.module_stock_service.common.exception.GlobalException;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
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
        if (stockCount  < 0) {
            throw new GlobalException(HttpStatus.CONFLICT, "재고 개수가 0보다 작습니다.");
        }
        this.stockCount = stockCount;
        return this;
    }

    public Stock validateStock() {
        if (stockCount  <= 0) {
            throw new GlobalException(HttpStatus.CONFLICT, "재고 개수가 0보다 같거나 작습니다.");
        }
        return this;
    }

    public Stock subtract(int quantity) {
        System.out.println(stockCount + " : " + quantity);
        if (stockCount < quantity) {
            throw new GlobalException(HttpStatus.CONFLICT, "재고수량이 부족합니다.");
        }
        stockCount = stockCount - quantity;
        return this;
    }

    public Stock add(int quantity) {
        stockCount = stockCount + quantity;
        return this;
    }
}
