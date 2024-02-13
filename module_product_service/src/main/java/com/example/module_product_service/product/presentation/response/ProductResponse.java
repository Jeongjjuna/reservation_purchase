package com.example.module_product_service.product.presentation.response;

import com.example.module_product_service.product.domain.Product;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductResponse {

    private String name;
    private String content;
    private Long price;

    @Builder
    public ProductResponse(
            final String name,
            final String content,
            final Long price
    ) {
        this.name = name;
        this.content = content;
        this.price = price;
    }

    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
                .name(product.getName())
                .content(product.getContent())
                .price(product.getPrice())
                .build();
    }
}
