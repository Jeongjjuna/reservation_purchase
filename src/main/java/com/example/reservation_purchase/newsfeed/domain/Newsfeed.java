package com.example.reservation_purchase.newsfeed.domain;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class Newsfeed {

    private Long id;
    private Long receiverId;
    private Long senderId;
    private NewsfeedType newsfeedType;
    private Long activityId;
    protected LocalDateTime createdAt;

    @Builder
    public Newsfeed(
            final Long id,
            final Long receiverId,
            final Long senderId,
            final NewsfeedType newsfeedType,
            final Long activityId,
            final LocalDateTime createdAt
    ) {
        this.id = id;
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.newsfeedType = newsfeedType;
        this.activityId = activityId;
        this.createdAt = createdAt;
    }

    public static Newsfeed create(final NewsfeedCreate newsfeedCreate) {

        NewsfeedType type = NewsfeedType.create(newsfeedCreate.getNewsfeedType());

        return Newsfeed.builder()
                .receiverId(newsfeedCreate.getReceiverId())
                .senderId(newsfeedCreate.getSenderId())
                .newsfeedType(type)
                .activityId(newsfeedCreate.getActivityId())
                .build();
    }
}
