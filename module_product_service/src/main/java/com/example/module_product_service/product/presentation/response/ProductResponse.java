package com.example.module_product_service.product.presentation.response;

import com.example.module_product_service.product.domain.Product;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ProductResponse {

    private Long productId;
    private String name;
    private String content;
    private Long price;

    @Builder
    public ProductResponse(
            final Long productId,
            final String name,
            final String content,
            final Long price,
            final LocalDateTime reservationStartAt
    ) {
        this.productId = productId;
        this.name = name;
        this.content = content;
        this.price = price;
    }

    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
                .productId(product.getId())
                .name(product.getName())
                .content(product.getContent())
                .price(product.getPrice())
                .build();
    }
}
