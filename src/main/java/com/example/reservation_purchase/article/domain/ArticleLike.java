package com.example.reservation_purchase.article.domain;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ArticleLike {

    private Long id;
    private Article article;
    private Long memberId;
    protected LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    @Builder
    public ArticleLike(
            final Long id,
            final Article article,
            final Long memberId,
            final LocalDateTime createdAt,
            final LocalDateTime deletedAt
    ) {
        this.id = id;
        this.article = article;
        this.memberId = memberId;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }
}
