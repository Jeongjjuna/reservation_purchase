package com.example.reservation_purchase.article.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleCreate {

    private String content;

    public ArticleCreate(final String content) {
        this.content = content;
    }
}
