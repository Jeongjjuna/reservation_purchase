package com.example.newsfeed_service.article.domain;

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
    protected LocalDateTime deletedAt;

    @Builder
    public Article(final Long id, final Long writerId, final String content, final LocalDateTime createdAt, final LocalDateTime updatedAt, final LocalDateTime deletedAt) {
        this.id = id;
        this.writerId = writerId;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Article create(final Long principalId, final ArticleCreate articleCreate) {
        return Article.builder()
                .writerId(principalId)
                .content(articleCreate.getContent())
                .build();
    }
}
