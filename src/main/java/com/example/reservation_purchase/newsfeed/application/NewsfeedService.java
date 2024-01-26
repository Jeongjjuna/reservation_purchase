package com.example.reservation_purchase.newsfeed.application;

import com.example.reservation_purchase.newsfeed.application.port.NewsfeedRepository;
import com.example.reservation_purchase.newsfeed.domain.Newsfeed;
import com.example.reservation_purchase.newsfeed.domain.NewsfeedCreate;
import org.springframework.stereotype.Service;

@Service
public class NewsfeedService {

    private final NewsfeedRepository newsfeedRepository;

    public NewsfeedService(final NewsfeedRepository newsfeedRepository) {
        this.newsfeedRepository = newsfeedRepository;
    }

    public void create(final NewsfeedCreate newsfeedCreate) {
        Newsfeed newsfeed = Newsfeed.create(newsfeedCreate);
        newsfeedRepository.save(newsfeed);
    }
}
