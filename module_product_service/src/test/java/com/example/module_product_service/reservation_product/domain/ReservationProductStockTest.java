package com.example.module_product_service.reservation_product.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("단위테스트 [ReservationProductStock]")
class ReservationProductStockTest {

    @DisplayName("수정 테스트")
    @Test
    void 수정테스트() {
        // given
        ReservationProductStock reservationProductStock = ReservationProductStock.builder()
                .productId(1L)
                .stockCount(50)
                .build();

        // when
        reservationProductStock.update(100);

        // then
        assertThat(reservationProductStock.getStockCount()).isEqualTo(100);
    }

    @DisplayName("생성 테스트")
    @Test
    void 생성테스트() {
        ReservationProductCreate reservationProductCreate = new ReservationProductCreate(
                "name",
                "content",
                10000L,
                LocalDateTime.now(),
                50
        );

        assertThatCode(() -> ReservationProductStock.create(1L, reservationProductCreate)
        ).doesNotThrowAnyException();
    }

}