package com.example.module_product_service.product.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

@DisplayName("단위테스트 [ProductStock]")
class ProductStockTest {

    @DisplayName("수정 테스트")
    @Test
    void 수정테스트() {
        // given
        ProductStock productStock = ProductStock.builder()
                .productId(1L)
                .stockCount(50)
                .build();

        // when
        productStock.update(100);

        // then
        assertThat(productStock.getStockCount()).isEqualTo(100);
    }

    @DisplayName("생성 테스트")
    @Test
    void 생성테스트() {
        ProductCreate productCreate = new ProductCreate(
                "name",
                "content",
                10000L,
                LocalDateTime.now(),
                50
        );

        assertThatCode(() -> ProductStock.create(1L, productCreate)
        ).doesNotThrowAnyException();
    }

}