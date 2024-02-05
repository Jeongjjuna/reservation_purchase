package com.example.activity_service.common.domain;

import lombok.Getter;

@Getter
public enum ActivityType {
    ARTICLE("article"),
    ARTICLE_LIKE("articleLike"),
    COMMENT("comment"),
    COMMENT_LIKE("commentLike"),
    FOLLOW("follow");

    private String activityType;

    ActivityType(final String activityType) {
        this.activityType = activityType;
    }
}
