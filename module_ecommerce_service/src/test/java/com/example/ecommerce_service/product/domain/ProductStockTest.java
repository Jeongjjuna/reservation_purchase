package com.example.ecommerce_service.product.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("단위테스트 [ProductStock]")
class ProductStockTest {

    @DisplayName("생성 테스트")
    @Test
    void 생성테스트() {
        ProductCreate productCreate = new ProductCreate(
                "name",
                "content",
                10000L,
                50
        );

        assertThatCode(() -> ProductStock.create(1L, productCreate)
        ).doesNotThrowAnyException();
    }
}