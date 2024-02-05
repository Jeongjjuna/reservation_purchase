package com.example.activity_service.follow.application.port;

import com.example.activity_service.follow.domain.Follow;
import java.util.List;
import java.util.Optional;

public interface FollowRepository {
    Follow save(Follow follow);

    List<Follow> findFollowing(Long followerId);

    List<Follow> findFollower(Long myId);

    Optional<Follow> findByFollowerAndFollowing(Long followerMemberId, Long followingMemberId);
}
