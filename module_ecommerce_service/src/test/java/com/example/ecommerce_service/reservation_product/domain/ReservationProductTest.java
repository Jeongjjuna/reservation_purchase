package com.example.ecommerce_service.reservation_product.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

@DisplayName("단위테스트 [ReservationProduct]")
class ReservationProductTest {

    @DisplayName("수정 테스트")
    @Test
    void 수정테스트() {
        // given
        ReservationProductCreate reservationProductCreate = new ReservationProductCreate(
                "name",
                "content",
                10000L,
                LocalDateTime.now(),
                50
        );
        ReservationProduct reservationProduct = ReservationProduct.create(reservationProductCreate);

        // when
        reservationProduct.update(new ReservationProductUpdate(
                "수정된 이름",
                "수정된 내용",
                20000L,
                LocalDateTime.now(),
                100
        ));

        // then
        assertAll(
                () -> assertThat(reservationProduct.getName()).isEqualTo("수정된 이름"),
                () -> assertThat(reservationProduct.getContent()).isEqualTo("수정된 내용"),
                () -> assertThat(reservationProduct.getPrice()).isEqualTo(20000L)
        );
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

        assertThatCode(() -> ReservationProduct.create(reservationProductCreate)
        ).doesNotThrowAnyException();
    }
}