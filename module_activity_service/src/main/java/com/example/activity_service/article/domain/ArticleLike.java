package com.example.activity_service.article.domain;

import com.example.activity_service.client.NewsfeedCreate;
import com.example.activity_service.common.domain.ActivityType;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class ArticleLike {

    private Long id;
    private Article article;
    private Long writerId;
    private boolean likeCheck;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public ArticleLike(
            final Long id,
            final Article article,
            final Long writerId,
            final boolean likeCheck,
            final LocalDateTime createdAt,
            final LocalDateTime deletedAt
    ) {
        this.id = id;
        this.article = article;
        this.writerId = writerId;
        this.likeCheck = likeCheck;
        this.createdAt = createdAt;
        this.updatedAt = deletedAt;
    }

    public static ArticleLike create(Article article, Long writerId) {
        return ArticleLike.builder()
                .article(article)
                .writerId(writerId)
                .build();
    }

    public NewsfeedCreate toNewsfeedCreate() {
        return NewsfeedCreate.builder()
                .receiverId(writerId)
                .senderId(writerId)
                .newsfeedType(ActivityType.ARTICLE_LIKE.getActivityType())
                .activityId(id)
                .build();
    }
}
