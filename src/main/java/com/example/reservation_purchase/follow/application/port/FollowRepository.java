package com.example.reservation_purchase.follow.application.port;

import com.example.reservation_purchase.follow.domain.Follow;
import java.util.List;
import java.util.Optional;

public interface FollowRepository {
    Follow save(Follow follow);

    List<Follow> findFollowing(Long followerId);

    List<Follow> findFollower(Long myId);

    Optional<Follow> findByFollowerAndFollowing(Long followerMemberId, Long followingMemberId);
}
