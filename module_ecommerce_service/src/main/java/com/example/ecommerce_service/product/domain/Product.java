package com.example.ecommerce_service.product.domain;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class Product {

    private Long id;
    private String name;
    private String content;
    private Long price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @Builder
    public Product(
            final Long id,
            final String name,
            final String content,
            final Long price,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt,
            final LocalDateTime deletedAt
    ) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Product create(final ProductCreate productCreate) {
        return Product.builder()
                .name(productCreate.getName())
                .content(productCreate.getContent())
                .price(productCreate.getPrice())
                .build();
    }

    public Product update(final ProductUpdate productUpdate) {
        this.name = productUpdate.getName();
        this.content = productUpdate.getContent();
        this.price = productUpdate.getPrice();
        return this;
    }
}
