package com.example.reservation_purchase.follow.domain;

import com.example.reservation_purchase.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class Follow {

    private Long id;
    private Member followerMember;
    private Member followingMember;
    private boolean followCheck;
    private LocalDateTime updatedAt;

    @Builder
    public Follow(
            final Long id,
            final Member followerMember,
            final Member followingMember,
            final boolean followCheck,
            final LocalDateTime updatedAt
    ) {
        this.id = id;
        this.followerMember = followerMember;
        this.followingMember = followingMember;
        this.followCheck = followCheck;
        this.updatedAt = updatedAt;
    }

    public static Follow create(final Member followerMember, final Member folloingMember) {
        return Follow.builder()
                .followerMember(followerMember)
                .followingMember(folloingMember)
                .build();
    }
}
