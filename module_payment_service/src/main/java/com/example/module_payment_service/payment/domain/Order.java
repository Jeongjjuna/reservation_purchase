package com.example.module_payment_service.payment.domain;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter

public class Order {

    private Long id;
    private Long productId;
    private ProductType productType;
    private Long quantity;
    private Long memberId;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    @Builder
    public Order(
            final Long id,
            final Long productId,
            final ProductType productType,
            final Long quantity,
            final Long memberId,
            final String address,
            final LocalDateTime createdAt,
            final LocalDateTime deletedAt
    ) {
        this.id = id;
        this.productId = productId;
        this.productType = productType;
        this.quantity = quantity;
        this.memberId = memberId;
        this.address = address;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    public Order cancel() {
        deletedAt = LocalDateTime.now();
        return this;
    }
}
