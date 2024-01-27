package com.example.reservation_purchase.article.domain;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class Comment {

    private Long id;
    private Article article;
    private Long writerId;
    private String content;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    @Builder
    public Comment(
            final Long id,
            final Article article,
            final Long writerId,
            final String content,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt
    ) {
        this.id = id;
        this.article = article;
        this.writerId = writerId;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Comment create(
            final CommentCreate commentCreate,
            final Article article,
            final Long principalId
    ) {
        return Comment.builder()
                .article(article)
                .writerId(principalId)
                .content(commentCreate.getContent())
                .build();
    }
}
