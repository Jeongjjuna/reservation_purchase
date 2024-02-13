package com.example.module_order_service.order.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("단위테스트 [Order]")
class OrderTest {

    @DisplayName("생성 테스트")
    @Test
    void 생성테스트() {
        OrderCreate orderCreate = new OrderCreate(
                1L,
                "reservationProduct",
                2L,
                4L,
                "서울특별시 xxx동 xxx아파트 xx호"
        );

        assertThatCode(() -> Order.create(orderCreate)
        ).doesNotThrowAnyException();
    }
}