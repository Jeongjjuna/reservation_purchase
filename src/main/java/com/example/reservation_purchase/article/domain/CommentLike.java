package com.example.reservation_purchase.article.domain;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class CommentLike {

    private Long id;
    private Comment comment;
    private Long memberId;
    protected LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    @Builder
    public CommentLike(
            final Long id,
            final Comment comment,
            final Long memberId,
            final LocalDateTime createdAt,
            final LocalDateTime deletedAt
    ) {
        this.id = id;
        this.comment = comment;
        this.memberId = memberId;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }
}
