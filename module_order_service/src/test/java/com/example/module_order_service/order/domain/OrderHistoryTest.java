package com.example.module_order_service.order.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.module_order_service.common.exception.GlobalException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

@DisplayName("단위테스트 [OrderHistory]")
class OrderHistoryTest {

    @DisplayName("주문 진행중이 아닌 주문을 취소할 때 예외 발생")
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
        OrderHistory orderHistory = OrderHistory.create(order);
        orderHistory.cancel();

        // when, then
        assertThatThrownBy(() -> orderHistory.cancel())
                .isInstanceOf(GlobalException.class);
    }

    @DisplayName("취소 테스트")
    @Test
    void cancel() {
        // given
        Order order = Order.builder()
                .id(1L)
                .productId(1L)
                .memberId(1L)
                .quantity(3)
                .price(3000)
                .address("address")
                .createdAt(LocalDateTime.now())
                .build();
        OrderHistory orderHistory = OrderHistory.create(order);

        // when
        orderHistory.cancel();

        // then
        assertThat(orderHistory.getStatus()).isEqualTo(Status.CANCELED);
    }

    @DisplayName("생성 테스트")
    @Test
    void create() {
        // given
        Order order = Order.builder()
                .id(1L)
                .productId(1L)
                .memberId(1L)
                .quantity(3)
                .price(3000)
                .address("address")
                .createdAt(LocalDateTime.now())
                .build();

        // when, then
        assertThatCode(() -> OrderHistory.create(order))
                .doesNotThrowAnyException();
    }
}