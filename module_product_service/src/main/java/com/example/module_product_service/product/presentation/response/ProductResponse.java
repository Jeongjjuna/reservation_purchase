package com.example.module_product_service.product.presentation.response;

import com.example.module_product_service.product.domain.Product;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ProductResponse {

    private String name;
    private String content;
    private Long price;
    private LocalDateTime purchaseButtonActivationAt;

    @Builder
    public ProductResponse(
            final String name,
            final String content,
            final Long price,
            final LocalDateTime purchaseButtonActivationAt
    ) {
        this.name = name;
        this.content = content;
        this.price = price;
        this.purchaseButtonActivationAt = purchaseButtonActivationAt;
    }

    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
                .name(product.getName())
                .content(product.getContent())
                .price(product.getPrice())
                .build();
    }
}
