package com.example.module_order_service.order.presentation.response;

import com.example.module_order_service.order.domain.Order;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class OrderResponse {

    private Long id;
    private Long productId;
    private Long memberId;
    private Long quantity;
    private Long price;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    @Builder
    public OrderResponse(
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

    public static OrderResponse from(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .productId(order.getProductId())
                .memberId(order.getMemberId())
                .quantity(order.getQuantity())
                .price(order.getPrice())
                .address(order.getAddress())
                .createdAt(order.getCreatedAt())
                .deletedAt(order.getDeletedAt())
                .build();
    }
}
