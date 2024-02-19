package com.example.module_product_service.product.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ProductUpdate {
    private String name;
    private String content;
    private Long price;
    private LocalDateTime purchaseButtonActivationAt;
    private Integer stockCount;

    public ProductUpdate(
            final String name,
            final String content,
            final Long price,
            final LocalDateTime purchaseButtonActivationAt,
            final Integer stockCount
    ) {
        this.name = name;
        this.content = content;
        this.price = price;
        this.purchaseButtonActivationAt = purchaseButtonActivationAt;
        this.stockCount = stockCount;
    }
}
