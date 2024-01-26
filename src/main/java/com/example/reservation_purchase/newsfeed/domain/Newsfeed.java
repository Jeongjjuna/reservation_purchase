package com.example.reservation_purchase.newsfeed.domain;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class Newsfeed {

    private Long id;
    private Long receiverId;
    private Long senderId;
    protected LocalDateTime createdAt;
    private boolean viewed;
    private NewsfeedType newsfeedType;

    @Builder
    public Newsfeed(final Long id, final Long receiverId, final Long senderId, final LocalDateTime createdAt, final boolean viewed, final NewsfeedType newsfeedType) {
        this.id = id;
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.createdAt = createdAt;
        this.viewed = viewed;
        this.newsfeedType = newsfeedType;
    }

    public static Newsfeed create(final NewsfeedCreate newsfeedCreate) {

        NewsfeedType type = NewsfeedType.create(newsfeedCreate.getNewsfeedType());

        return Newsfeed.builder()
                .receiverId(newsfeedCreate.getReceiverId())
                .senderId(newsfeedCreate.getSenderId())
                .newsfeedType(type)
                .build();
    }
}
