package com.example.ecommerce_service.product.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductCreate {

    private String name;
    private String content;
    private Long price;

    public ProductCreate(
            final String name,
            final String content,
            final Long price
    ) {
        this.name = name;
        this.content = content;
        this.price = price;
    }
}
