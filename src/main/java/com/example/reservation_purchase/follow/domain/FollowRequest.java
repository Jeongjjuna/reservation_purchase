package com.example.reservation_purchase.follow.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowRequest {

    private Long followerMemberId;
    private Long followingMemberId;

    @Builder
    public FollowRequest(final Long followerMemberId, final Long followingMemberId) {
        this.followerMemberId = followerMemberId;
        this.followingMemberId = followingMemberId;
    }
}
