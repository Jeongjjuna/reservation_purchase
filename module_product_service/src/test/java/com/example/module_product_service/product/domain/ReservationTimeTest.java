package com.example.module_product_service.product.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

@DisplayName("단위테스트 [ReservationTime]")
class ReservationTimeTest {

    @DisplayName("현재 시간이 예약 시작 이후 라면 true 반환")
    @Test
    void name() {
        // given
        ReservationTime reservationTime = ReservationTime.builder()
                .id(1L)
                .productId(1L)
                .createdAt(LocalDateTime.now())
                .reservationStartAt(LocalDateTime.now())
                .build();

        // when
        boolean result = reservationTime.isAfterReservationStartAt();

        // then
        assertThat(result).isTrue();
    }
}