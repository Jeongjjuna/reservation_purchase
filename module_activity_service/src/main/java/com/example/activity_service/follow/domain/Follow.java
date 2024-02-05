package com.example.activity_service.follow.domain;

import com.example.activity_service.client.NewsfeedCreate;
import com.example.activity_service.common.domain.ActivityType;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class Follow {

    private Long id;
    private Long followerMemberId;
    private Long followingMemberId;
    private boolean followCheck;
    private LocalDateTime updatedAt;

    @Builder
    public Follow(
            final Long id,
            final Long followerMemberId,
            final Long followingMemberId,
            final boolean followCheck,
            final LocalDateTime updatedAt
    ) {
        this.id = id;
        this.followerMemberId = followerMemberId;
        this.followingMemberId = followingMemberId;
        this.followCheck = followCheck;
        this.updatedAt = updatedAt;
    }

    public static Follow create(final Long followerMember, final Long followingMember) {
        return Follow.builder()
                .followerMemberId(followerMember)
                .followingMemberId(followingMember)
                .build();
    }

    public NewsfeedCreate toNewsfeedCreate() {
        return NewsfeedCreate.builder()
                .receiverId(followerMemberId)
                .senderId(followingMemberId)
                .newsfeedType(ActivityType.FOLLOW.getActivityType())
                .activityId(id)
                .build();
    }
}
