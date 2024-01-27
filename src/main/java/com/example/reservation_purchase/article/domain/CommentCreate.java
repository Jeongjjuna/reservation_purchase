package com.example.reservation_purchase.article.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentCreate {

    private Long articleId;
    private String content;

    public CommentCreate(final Long articleId, final String content) {
        this.articleId = articleId;
        this.content = content;
    }
}
