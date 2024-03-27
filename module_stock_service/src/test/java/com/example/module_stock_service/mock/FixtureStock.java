package com.example.module_stock_service.mock;

import com.example.module_stock_service.common.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class FixtureStock {
    private Long productId;
    private volatile int stockCount;

    public FixtureStock(final Long productId, final Integer stockCount) {
        this.productId = productId;
        this.stockCount = stockCount;
    }

    /**
     * synchronized 임계영역 안쪽과 바깥쪽에서 if문 검사를 해주는 방식
     */
    public FixtureStock subtractCase1(int quantity) throws Exception {
        if (stockCount < quantity) {
            throw new GlobalException(HttpStatus.CONFLICT, "재고수량이 부족합니다.");
        }

        synchronized (this) {
            if (stockCount < quantity) {
                throw new GlobalException(HttpStatus.CONFLICT, "재고수량이 부족합니다.");
            }
            stockCount = stockCount - quantity;
        }
        return this;
    }

    /**
     * synchronized 바깥쪽에서만 if문 검사를 해주는 방식
     */
    public FixtureStock subtractCase2(int quantity, int temp) throws Exception {
        if (stockCount < quantity) {
            throw new GlobalException(HttpStatus.CONFLICT, "재고수량이 부족합니다.");
        }

        if (temp == 99) {
            Thread.sleep(3000);
        }

        synchronized (this) {
            stockCount = stockCount - quantity;
        }
        return this;
    }

    public Long getProductId() {
        return productId;
    }

    public int getStockCount() {
        return stockCount;
    }
}
