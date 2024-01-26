package com.example.reservation_purchase.follow.application.port;

import com.example.reservation_purchase.follow.domain.Follow;
import java.util.List;

public interface FollowRepository {
    Follow save(Follow follow);

    List<Follow> findByFollowingMember(Long followerId);
}
