package com.example.ecommerce_service.reservation_product.presentation.response;

import com.example.ecommerce_service.reservation_product.domain.ReservationProduct;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ReservationProductResponse {

    private String name;
    private String content;
    private Long price;
    private LocalDateTime purchaseButtonActivationAt;

    @Builder
    public ReservationProductResponse(
            final String name,
            final String content,
            final Long price,
            final LocalDateTime purchaseButtonActivationAt
    ) {
        this.name = name;
        this.content = content;
        this.price = price;
        this.purchaseButtonActivationAt = purchaseButtonActivationAt;
    }

    public static ReservationProductResponse from(ReservationProduct reservationProduct) {
        return ReservationProductResponse.builder()
                .name(reservationProduct.getName())
                .content(reservationProduct.getContent())
                .price(reservationProduct.getPrice())
                .purchaseButtonActivationAt(reservationProduct.getPurchaseButtonActivationAt())
                .build();
    }
}
