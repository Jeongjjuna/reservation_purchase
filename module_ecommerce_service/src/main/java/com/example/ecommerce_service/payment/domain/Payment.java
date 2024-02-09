package com.example.ecommerce_service.payment.domain;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class Payment {

    private Long id;
    private Long orderId;
    private Long memberId;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    @Builder
    public Payment(
            final Long id,
            final Long orderId,
            final Long memberId,
            final LocalDateTime createdAt,
            final LocalDateTime deletedAt
    ) {
        this.id = id;
        this.orderId = orderId;
        this.memberId = memberId;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    public static Payment create(final PaymentCreate paymentCreate) {
        return Payment.builder()
                .orderId(paymentCreate.getOrderId())
                .memberId(paymentCreate.getMemberId())
                .build();
    }
}
