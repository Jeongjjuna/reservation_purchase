package com.example.ecommerce_service.product.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("단위테스트 [Product]")
class ProductTest {


    @DisplayName("수정 테스트")
    @Test
    void 수정테스트() {
        // given
        ProductCreate productCreate = new ProductCreate(
                "name",
                "content",
                10000L,
                50
        );
        Product product = Product.create(productCreate);

        // when
        product.update(new ProductUpdate(
                "수정된 이름",
                "수정된 내용",
                20000L,
                100
        ));

        // then
        assertAll(
                () -> assertThat(product.getName()).isEqualTo("수정된 이름"),
                () -> assertThat(product.getContent()).isEqualTo("수정된 내용"),
                () -> assertThat(product.getPrice()).isEqualTo(20000L)
        );
    }

    @DisplayName("생성 테스트")
    @Test
    void 생성테스트() {
        ProductCreate productCreate = new ProductCreate(
                "name",
                "content",
                10000L,
                50
        );

        assertThatCode(() -> Product.create(productCreate)
        ).doesNotThrowAnyException();
    }
}