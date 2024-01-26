package com.example.reservation_purchase.newsfeed.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NewsfeedCreate {

    private Long receiverId;
    private Long senderId;
    private String newsfeedType;

    @Builder
    public NewsfeedCreate(final Long receiverId, final Long senderId, final String newsfeedType) {
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.newsfeedType = newsfeedType;
    }
}
