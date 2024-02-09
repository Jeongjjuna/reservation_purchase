package com.example.ecommerce_service.payment.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PaymentCreate {

    private Long orderId;
    private Long memberId;

    public PaymentCreate(final Long orderId, final Long memberId) {
        this.orderId = orderId;
        this.memberId = memberId;
    }
}
