package com.example.activity_service.comment.domain;

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
