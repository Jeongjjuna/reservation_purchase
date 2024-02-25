package com.example.module_product_service.product.domain;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ReservationTime {

    private Long id;
    private Long productId;
    private LocalDateTime createdAt;
    private LocalDateTime reservationStartAt;

    @Builder
    public ReservationTime(
            final Long id,
            final Long productId,
            final LocalDateTime createdAt,
            final LocalDateTime reservationStartAt
    ) {
        this.id = id;
        this.productId = productId;
        this.createdAt = createdAt;
        this.reservationStartAt = reservationStartAt;
    }

    public static ReservationTime create(final Product product, final ProductCreate productCreate) {
        return ReservationTime.builder()
                .productId(product.getId())
                .reservationStartAt(productCreate.getReservationStartAt())
                .build();
    }

    public Boolean isAfterReservationStartAt() {
        LocalDateTime currentTime = LocalDateTime.now();
        return reservationStartAt.isBefore(currentTime);
    }
}
