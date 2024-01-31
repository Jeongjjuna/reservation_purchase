package com.example.activity_service.newsfeed.domain;

import lombok.Getter;
import java.util.Arrays;

@Getter
public enum NewsfeedType {
    ARTICLE("article"),
    ARTICLE_LIKE("articleLike"),
    COMMENT("comment"),
    COMMENT_LIKE("commentLike"),
    FOLLOW("follow");

    private final String newsfeedType;

    NewsfeedType(final String newsfeedType) {
        this.newsfeedType = newsfeedType;
    }

    /**
     * 존재하는 문자열 형태만 생성한다.
     */
    public static NewsfeedType create(String newsfeedType) {
        return Arrays.stream(NewsfeedType.values())
                .filter(value -> value.newsfeedType.equals(newsfeedType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] Unknown NewsfeedType: " + newsfeedType));
    }
}