package com.example.ecommerce_service.product.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductCreate {

    private String name;
    private String content;
    private Long price;
    private Integer stockCount;

    public ProductCreate(
            final String name,
            final String content,
            final Long price,
            final Integer stockCount
    ) {
        this.name = name;
        this.content = content;
        this.price = price;
        this.stockCount = stockCount;
    }
}
