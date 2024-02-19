package com.example.module_order_service.order.domain;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class OrderHistory {

    private Long orderId;
    private Long productId;
    private Long memberId;
    private Long quantity;
    private Long price;
    private String address;
    private Status status;
    private LocalDateTime createdAt;

    @Builder
    public OrderHistory(
            final Long orderId,
            final Long productId,
            final Long memberId,
            final Long quantity,
            final Long price,
            final String address,
            final Status status,
            final LocalDateTime createdAt
    ) {
        this.orderId = orderId;
        this.productId = productId;
        this.memberId = memberId;
        this.quantity = quantity;
        this.price = price;
        this.address = address;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static OrderHistory create(final Order order) {
        return OrderHistory.builder()
                .orderId(order.getId())
                .productId(order.getProductId())
                .memberId(order.getMemberId())
                .quantity(order.getQuantity())
                .price(order.getPrice())
                .address(order.getAddress())
                .status(Status.PROCESSING)
                .build();
    }
}
