package com.example.module_stock_service.stock.presentation.response;

import com.example.module_stock_service.stock.domain.Stock;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StockResponse {

    private Long productId;
    private Integer stockCount;

    @Builder
    public StockResponse(final Long productId, final Integer stockCount) {
        this.productId = productId;
        this.stockCount = stockCount;
    }

    public static StockResponse from(Stock productStock) {
        return StockResponse.builder()
                .productId(productStock.getProductId())
                .stockCount(productStock.getStockCount())
                .build();
    }
}