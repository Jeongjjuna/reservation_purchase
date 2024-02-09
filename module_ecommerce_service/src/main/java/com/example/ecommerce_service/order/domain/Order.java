package com.example.ecommerce_service.order.domain;

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

    public static Order create(final OrderCreate orderCreate) {
        return Order.builder()
                .productId(orderCreate.getProductId())
                .productType(ProductType.create(orderCreate.getProductType()))
                .quantity(orderCreate.getQuantity())
                .memberId(orderCreate.getMemberId())
                .address(orderCreate.getAddress())
                .build();
    }
}
