package com.example.activity_service.article.domain;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ArticleLike {

    private Long id;
    private Article article;
    private Long memberId;
    private boolean likeCheck;
    protected LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public ArticleLike(
            final Long id,
            final Article article,
            final Long memberId,
            final boolean likeCheck,
            final LocalDateTime createdAt,
            final LocalDateTime deletedAt
    ) {
        this.id = id;
        this.article = article;
        this.memberId = memberId;
        this.likeCheck = likeCheck;
        this.createdAt = createdAt;
        this.updatedAt = deletedAt;
    }
}
