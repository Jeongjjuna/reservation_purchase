package com.example.ecommerce_service.order.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCreate {

    private Long productId;
    private String productType;
    private Long quantity;
    private Long memberId;
    private String address;

    public OrderCreate(
            final Long productId,
            final String productType,
            final Long quantity,
            final Long memberId,
            final String address
    ) {
        this.productId = productId;
        this.productType = productType;
        this.quantity = quantity;
        this.memberId = memberId;
        this.address = address;
    }
}
