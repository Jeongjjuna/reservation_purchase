package com.example.module_payment_service.payment.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("단위테스트 [ProductType]")
class ProductTypeTest {

    @DisplayName("생성 테스트")
    @Test
    void create() {
        // when
        ProductType type = ProductType.create("product");

        // then
        assertThat(type).isEqualTo(ProductType.PRODUCT);
    }
}