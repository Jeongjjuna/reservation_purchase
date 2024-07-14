package org.example.order_service_v2.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderCreate {

    private Long productId;
    private Long memberId;
    private Integer quantity;
    private String address;

    public OrderCreate(
            final Long productId,
            final Long memberId,
            final Integer quantity,
            final String address
    ) {
        this.productId = productId;
        this.memberId = memberId;
        this.quantity = quantity;
        this.address = address;
    }
}
