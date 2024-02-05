package com.example.newsfeed_service.newsfeed.domain;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class Newsfeed {

    private String id;
    private Long receiverId;
    private Long senderId;
    private NewsfeedType newsfeedType;
    private Long activityId;
    protected LocalDateTime createdAt;

    @Builder
    public Newsfeed(
            final String id,
            final Long receiverId,
            final Long senderId,
            final Long activityId,
            final NewsfeedType newsfeedType,
            final LocalDateTime createdAt
    ) {
        this.id = id;
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.activityId = activityId;
        this.newsfeedType = newsfeedType;
        this.createdAt = createdAt;
    }

    public static Newsfeed create(final NewsfeedCreate newsfeedCreate) {

        final NewsfeedType type = NewsfeedType.create(newsfeedCreate.getNewsfeedType());

        return Newsfeed.builder()
                .receiverId(newsfeedCreate.getReceiverId())
                .senderId(newsfeedCreate.getSenderId())
                .newsfeedType(type)
                .activityId(newsfeedCreate.getActivityId())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
