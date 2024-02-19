package com.example.module_order_service.order.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCreate {

    private Long productId;
    private Long memberId;
    private Long quantity;
    private String address;

    public OrderCreate(
            final Long productId,
            final Long memberId,
            final Long quantity,
            final String address
    ) {
        this.productId = productId;
        this.memberId = memberId;
        this.quantity = quantity;
        this.address = address;
    }
}
