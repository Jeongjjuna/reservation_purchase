package com.example.reservation_purchase.newsfeed.application.port;

import com.example.reservation_purchase.newsfeed.domain.Newsfeed;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface NewsfeedRepository {

    Newsfeed save(Newsfeed newsfeed);

    Page<Newsfeed> findAllByFollowingIds(List<Long> followingIds, Pageable pageable);
}