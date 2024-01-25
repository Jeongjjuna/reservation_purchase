package com.example.reservation_purchase.follow.application.port;

import com.example.reservation_purchase.follow.domain.Follow;

public interface FollowRepository {
    Follow save(Follow follow);
}
