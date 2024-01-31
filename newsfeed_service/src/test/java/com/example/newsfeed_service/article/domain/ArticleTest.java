package com.example.newsfeed_service.article.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import com.example.newsfeed_service.article.domain.Article;
import com.example.newsfeed_service.article.domain.ArticleCreate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("단위테스트 [Article]")
class ArticleTest {

    @DisplayName("생성 테스트")
    @Test
    void 생성테스트() {
        ArticleCreate articleCreate = new ArticleCreate("content");

        assertThatCode(() -> Article.create(1L, articleCreate)
        ).doesNotThrowAnyException();
    }
}