package com.example.reservation_purchase.article.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("단위테스트 [Comment]")
class CommentTest {

    @DisplayName("생성 테스트")
    @Test
    void 생성테스트() {
        // given
        CommentCreate commentCreate = new CommentCreate(1L, "content");
        Article article = Article.builder()
                .id(1L)
                .writerId(1L)
                .content("content")
                .build();

        // when, then
        assertThatCode(() -> Comment.create(commentCreate, article, 2L)
        ).doesNotThrowAnyException();
    }
}