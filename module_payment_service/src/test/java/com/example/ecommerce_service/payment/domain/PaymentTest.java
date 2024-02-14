package com.example.ecommerce_service.payment.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import com.example.module_payment_service.payment.domain.Payment;
import com.example.module_payment_service.payment.domain.PaymentCreate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("단위테스트 [Payment]")
class PaymentTest {

    @DisplayName("생성 테스트")
    @Test
    void 생성테스트() {
        PaymentCreate paymentCreate = new PaymentCreate(1L, 3L);

        assertThatCode(() -> Payment.create(paymentCreate)
        ).doesNotThrowAnyException();
    }
}