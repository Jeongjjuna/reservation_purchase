package com.example.module_order_service.order.presentation.response;

import com.example.module_order_service.order.domain.Order;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OrderResponse {

    private Long id;
    private Long productId;
    private String productType;
    private Long quantity;
    private Long memberId;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    @Builder
    public OrderResponse(
            final Long id,
            final Long productId,
            final String productType,
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

    public static OrderResponse from(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .productId(order.getProductId())
                .productType(order.getProductType().getProductType())
                .quantity(order.getQuantity())
                .memberId(order.getMemberId())
                .address(order.getAddress())
                .createdAt(order.getCreatedAt())
                .deletedAt(order.getDeletedAt())
                .build();
    }
}
