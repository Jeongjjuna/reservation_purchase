package com.example.activity_service.comment.domain;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class CommentLike {

    private Long id;
    private Comment comment;
    private Long writerId;
    private boolean likeCheck;
    protected LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public CommentLike(
            final Long id,
            final Comment comment,
            final Long writerId,
            final boolean likeCheck,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt
    ) {
        this.id = id;
        this.comment = comment;
        this.writerId = writerId;
        this.likeCheck = likeCheck;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
