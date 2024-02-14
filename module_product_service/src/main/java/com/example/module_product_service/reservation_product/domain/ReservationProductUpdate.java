package com.example.module_product_service.reservation_product.domain;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationProductUpdate {
    private String name;
    private String content;
    private Long price;
    private LocalDateTime purchaseButtonActivationAt;
    private Integer stockCount;

    public ReservationProductUpdate(
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
