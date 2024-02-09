package com.example.ecommerce_service.order.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("단위테스트 [ProductType]")
class ProductTypeTest {

    @DisplayName("생성 테스트")
    @Test
    void 생성테스트() {
        // given
        String input = "reservationProduct";

        // when
        ProductType productType = ProductType.create(input);

        // then
        assertThat(productType).isEqualTo(ProductType.RESERVATION_PRODUCT);
    }

}