package com.example.module_order_service.order.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.module_order_service.common.exception.GlobalException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("단위테스트 [Order]")
class OrderTest {

    @DisplayName("주문이 최소된 상태가 아니면  False반환")
    @Test
    void isCanceled_false() {
        // given
        OrderCreate orderCreate = new OrderCreate(
                1L,
                2L,
                4,
                "서울특별시 xxx동 xxx아파트 xx호"
        );
        Order order = Order.create(orderCreate, 8000);

        // when
        boolean result = order.isCanceled();

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("주문이 최소된 상태면  True반환")
    @Test
    void isCanceled_true() {
        // given
        OrderCreate orderCreate = new OrderCreate(
                1L,
                2L,
                4,
                "서울특별시 xxx동 xxx아파트 xx호"
        );
        Order order = Order.create(orderCreate, 8000);

        // when
        order.cancel();
        boolean result = order.isCanceled();

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("이미 취소된 주문을 취소할 시 예외 발생")
    @Test
    void cancel_exception() {
        // given
        OrderCreate orderCreate = new OrderCreate(
                1L,
                2L,
                4,
                "서울특별시 xxx동 xxx아파트 xx호"
        );
        Order order = Order.create(orderCreate, 8000);
        order.cancel();

        // when, then
        assertThatThrownBy(() -> order.cancel())
                .isInstanceOf(GlobalException.class);
    }

    @DisplayName("취소 테스트")
    @Test
    void cancel() {
        // given
        OrderCreate orderCreate = new OrderCreate(
                1L,
                2L,
                4,
                "서울특별시 xxx동 xxx아파트 xx호"
        );
        Order order = Order.create(orderCreate, 8000);

        // when
        order.cancel();


        // then
        assertThat(order.getDeletedAt()).isNotNull();
    }

    @DisplayName("생성 테스트")
    @Test
    void create() {
        OrderCreate orderCreate = new OrderCreate(
                1L,
                2L,
                4,
                "서울특별시 xxx동 xxx아파트 xx호"
        );
        Integer price = 8000;

        assertThatCode(() -> Order.create(orderCreate, price)
        ).doesNotThrowAnyException();
    }
}