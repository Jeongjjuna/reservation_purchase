package com.example.user_service.follow.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowCreate {

    private Long followerMemberId;
    private Long followingMemberId;

    @Builder
    public FollowCreate(final Long followerMemberId, final Long followingMemberId) {
        this.followerMemberId = followerMemberId;
        this.followingMemberId = followingMemberId;
    }
}
