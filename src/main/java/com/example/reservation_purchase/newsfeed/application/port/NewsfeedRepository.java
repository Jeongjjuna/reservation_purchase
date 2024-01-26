package com.example.reservation_purchase.newsfeed.application.port;

import com.example.reservation_purchase.newsfeed.domain.Newsfeed;

public interface NewsfeedRepository {
    Newsfeed save(Newsfeed newsfeed);
}
