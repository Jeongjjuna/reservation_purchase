package com.example.newsfeed_service.newsfeed.presentation.response;

import com.example.newsfeed_service.newsfeed.domain.NewsfeedType;
import com.example.newsfeed_service.newsfeed.domain.Newsfeed;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class NewsfeedResponse {

    private String id;
    private Long receiverId;
    private Long senderId;
    protected LocalDateTime createdAt;
    private NewsfeedType newsfeedType;

    @Builder
    public NewsfeedResponse(
            final String id,
            final Long receiverId,
            final Long senderId,
            final LocalDateTime createdAt,
            final NewsfeedType newsfeedType
    ) {
        this.id = id;
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.createdAt = createdAt;
        this.newsfeedType = newsfeedType;
    }

    public static NewsfeedResponse from(final Newsfeed newsfeed) {
        return NewsfeedResponse.builder()
                .id(newsfeed.getId())
                .receiverId(newsfeed.getReceiverId())
                .senderId(newsfeed.getSenderId())
                .createdAt(newsfeed.getCreatedAt())
                .newsfeedType(newsfeed.getNewsfeedType())
                .build();
    }
}
