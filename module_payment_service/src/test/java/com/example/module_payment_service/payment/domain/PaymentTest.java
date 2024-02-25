package com.example.module_payment_service.payment.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("단위테스트 [Payment]")
class PaymentTest {

    @DisplayName("취소 테스트")
    @Test
    void 취소테스트() {
        // given
        PaymentCreate paymentCreate = new PaymentCreate(1L, 3L);
        Payment payment = Payment.create(paymentCreate);

        // when
        payment.cancel();

        // then
        assertThat(payment.getDeletedAt()).isNotNull();
    }

    @DisplayName("생성 테스트")
    @Test
    void 생성테스트() {
        // given
        PaymentCreate paymentCreate = new PaymentCreate(1L, 3L);

        // when, then
        assertThatCode(() -> Payment.create(paymentCreate)
        ).doesNotThrowAnyException();
    }
}