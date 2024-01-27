package com.example.reservation_purchase.article.domain;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class Article {

    private Long id;
    private Long writerId;
    private String content;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    @Builder
    public Article(final Long id, final Long writerId, final String content, final LocalDateTime createdAt, final LocalDateTime updatedAt) {
        this.id = id;
        this.writerId = writerId;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
