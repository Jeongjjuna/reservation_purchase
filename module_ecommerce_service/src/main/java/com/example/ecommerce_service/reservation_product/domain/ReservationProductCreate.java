package com.example.ecommerce_service.reservation_product.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReservationProductCreate {

    private String name;
    private String content;
    private Long price;
    private LocalDateTime purchaseButtonActivationAt;
    private Integer stockCount;

    public ReservationProductCreate(
            final String name,
            final String content,
            final Long price,
            final LocalDateTime purchaseButtonActivationAt,
            final Integer stockCount
    ) {
        this.name = name;
        this.content = content;
        this.price = price;
        this.purchaseButtonActivationAt = purchaseButtonActivationAt;
        this.stockCount = stockCount;
    }
}
