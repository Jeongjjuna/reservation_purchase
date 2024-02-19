package com.example.module_order_service.order.domain;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class Order {

    private Long id;
    private Long productId;
    private Long memberId;
    private Long quantity;
    private Long price;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    @Builder
    public Order(
            final Long id,
            final Long productId,
            final Long memberId,
            final Long quantity,
            final Long price,
            final String address,
            final LocalDateTime createdAt,
            final LocalDateTime deletedAt
    ) {
        this.id = id;
        this.productId = productId;
        this.memberId = memberId;
        this.quantity = quantity;
        this.price = price;
        this.address = address;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    public static Order create(final OrderCreate orderCreate, final Long price) {
        return Order.builder()
                .productId(orderCreate.getProductId())
                .memberId(orderCreate.getMemberId())
                .quantity(orderCreate.getQuantity())
                .price(price)
                .address(orderCreate.getAddress())
                .build();
    }

    public Order cancel() {
        deletedAt = LocalDateTime.now();
        return this;
    }
}
