package com.example.module_product_service.product.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ProductCreate {

    private String name;
    private String content;
    private Long price;
    private LocalDateTime reservationStartAt;
    private Integer stockCount;

    public ProductCreate(
            final String name,
            final String content,
            final Long price,
            final LocalDateTime reservationStartAt,
            final Integer stockCount
    ) {
        this.name = name;
        this.content = content;
        this.price = price;
        this.reservationStartAt = reservationStartAt;
        this.stockCount = stockCount;
    }
}
