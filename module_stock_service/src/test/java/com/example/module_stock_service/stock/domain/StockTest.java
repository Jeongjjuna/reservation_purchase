package com.example.module_stock_service.stock.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.module_stock_service.common.exception.GlobalException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("단위테스트 [ProductStock]")
class StockTest {

    @DisplayName("재고 증가 성공")
    @Test
    void addStock() {
        // given
        Stock stock = Stock.builder()
                .productId(1L)
                .stockCount(10)
                .build();

        // when
        stock.add(6);

        // then
        assertThat(stock.getStockCount()).isEqualTo(16);
    }

    @DisplayName("재고 감소 성공")
    @Test
    void subtractStock() {
        // given
        Stock stock = Stock.builder()
                .productId(1L)
                .stockCount(10)
                .build();

        // when
        stock.subtract(6);

        // then
        assertThat(stock.getStockCount()).isEqualTo(4);
    }

    @DisplayName("재고 감소시 기존 수량보다 많은 양을 감소시키는 경우 예외 발생")
    @Test
    void subtractStock_exception() {
        // given
        Stock stock = Stock.builder()
                .productId(1L)
                .stockCount(10)
                .build();

        // when, then
        assertThatThrownBy(() -> stock.subtract(99))
                .isInstanceOf(GlobalException.class);
    }

    @DisplayName("재고 수량이 0보다 같거나 작으면 예외 발생")
    @ParameterizedTest(name = "재고수량이 \"{0}\" 일 때")
    @ValueSource(ints = {-999, -1})
    void validateStock(int input) {
        // given
        Stock stock = Stock.builder()
                .productId(1L)
                .stockCount(input)
                .build();

        // when, then
        assertThatThrownBy(() -> stock.validateStock())
                .isInstanceOf(GlobalException.class);
    }

    @DisplayName("수정 시 0보다 작으면 예외 발생")
    @ParameterizedTest(name = "수정 요청 재고수량이 \"{0}\" 일 때")
    @ValueSource(ints = {-999, -1})
    void update_exception(int input) {
        // given
        Stock stock = Stock.builder()
                .productId(1L)
                .stockCount(50)
                .build();

        // when, then
        assertThatThrownBy(() -> stock.update(input))
                .isInstanceOf(GlobalException.class);
    }

    @DisplayName("수정 시 0보다 같거나 크면 성공")
    @ParameterizedTest(name = "수정 요청 재고수량이 \"{0}\" 일 때")
    @ValueSource(ints = {0, 99})
    void update(int input) {
        // given
        Stock stock = Stock.builder()
                .productId(1L)
                .stockCount(50)
                .build();

        // when
        stock.update(input);

        // then
        assertThat(stock.getStockCount()).isEqualTo(input);
    }

    @DisplayName("생성 테스트")
    @Test
    void create() {
        Integer stockCount = 10;

        assertThatCode(() -> Stock.create(1L, stockCount)
        ).doesNotThrowAnyException();
    }

}