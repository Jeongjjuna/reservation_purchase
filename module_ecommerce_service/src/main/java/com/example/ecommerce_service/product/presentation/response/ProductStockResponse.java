package com.example.ecommerce_service.product.presentation.response;

import com.example.ecommerce_service.product.domain.ProductStock;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductStockResponse {

    private Long productId;
    private Integer stockCount;

    @Builder
    public ProductStockResponse(final Long productId, final Integer stockCount) {
        this.productId = productId;
        this.stockCount = stockCount;
    }

    public static ProductStockResponse from(ProductStock productStock) {
        return ProductStockResponse.builder()
                .productId(productStock.getProductId())
                .stockCount(productStock.getStockCount())
                .build();
    }
}
