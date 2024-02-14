package com.example.module_product_service.reservation_product.domain;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReservationProduct {

    private Long id;
    private String name;
    private String content;
    private Long price;
    private LocalDateTime purchaseButtonActivationAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    @Builder
    public ReservationProduct(
            final Long id,
            final String name,
            final String content,
            final Long price,
            final LocalDateTime purchaseButtonActivationAt,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt,
            final LocalDateTime deletedAt
    ) {
        this.id = id;
        this.name = name;
        this.content = content;
        this.price = price;
        this.purchaseButtonActivationAt = purchaseButtonActivationAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static ReservationProduct create(final ReservationProductCreate reservationProductCreate) {
        return ReservationProduct.builder()
                .name(reservationProductCreate.getName())
                .content(reservationProductCreate.getContent())
                .price(reservationProductCreate.getPrice())
                .purchaseButtonActivationAt(reservationProductCreate.getPurchaseButtonActivationAt())
                .build();
    }

    public ReservationProduct update(final ReservationProductUpdate reservationProductUpdate) {
        this.name = reservationProductUpdate.getName();
        this.content = reservationProductUpdate.getContent();
        this.price = reservationProductUpdate.getPrice();
        this.purchaseButtonActivationAt = reservationProductUpdate.getPurchaseButtonActivationAt();
        return this;
    }
}
