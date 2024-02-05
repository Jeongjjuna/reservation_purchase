package com.example.activity_service.comment.domain;

import com.example.activity_service.article.domain.Article;
import com.example.activity_service.client.NewsfeedCreate;
import com.example.activity_service.common.domain.ActivityType;
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
    protected LocalDateTime deletedAt;

    @Builder
    public Comment(
            final Long id,
            final Article article,
            final Long writerId,
            final String content,
            final LocalDateTime createdAt,
            final LocalDateTime updatedAt,
            final LocalDateTime deletedAt
    ) {
        this.id = id;
        this.article = article;
        this.writerId = writerId;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
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

    public NewsfeedCreate toNewsfeedCreate() {
        return NewsfeedCreate.builder()
                .receiverId(article.getWriterId())
                .senderId(writerId)
                .newsfeedType(ActivityType.COMMENT.getActivityType())
                .activityId(id)
                .build();
    }
}
