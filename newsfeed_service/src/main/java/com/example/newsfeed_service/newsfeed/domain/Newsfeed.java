package com.example.newsfeed_service.newsfeed.domain;

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

        NewsfeedType type = NewsfeedType.create(newsfeedCreate.getNewsfeedType());

        return Newsfeed.builder()
                .receiverId(newsfeedCreate.getReceiverId())
                .senderId(newsfeedCreate.getSenderId())
                .newsfeedType(type)
                .activityId(newsfeedCreate.getActivityId())
                .build();
    }

    @Override
    public String toString() {
        return "Newsfeed{" +
                "id=" + id +
                ", receiverId=" + receiverId +
                ", senderId=" + senderId +
                ", newsfeedType=" + newsfeedType +
                ", activityId=" + activityId +
                ", createdAt=" + createdAt +
                '}';
    }
}
