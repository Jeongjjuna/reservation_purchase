package com.example.module_stock_service.stock.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("단위테스트 [ProductStock]")
class StockTest {

    @DisplayName("수정 테스트")
    @Test
    void 수정테스트() {
        // given
        Stock productStock = Stock.builder()
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
        Integer stockCount = 10;

        assertThatCode(() -> Stock.create(1L, stockCount)
        ).doesNotThrowAnyException();
    }

}