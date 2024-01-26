package com.example.reservation_purchase.newsfeed.infrastructure;

import com.example.reservation_purchase.newsfeed.application.port.NewsfeedRepository;
import com.example.reservation_purchase.newsfeed.domain.Newsfeed;
import com.example.reservation_purchase.newsfeed.infrastructure.entity.NewsfeedEntity;
import org.springframework.stereotype.Repository;

@Repository
public class NewsfeedRepositoryImpl implements NewsfeedRepository {

    private final NewsfeedJpaRepository newsfeedJpaRepository;

    public NewsfeedRepositoryImpl(final NewsfeedJpaRepository newsfeedJpaRepository) {
        this.newsfeedJpaRepository = newsfeedJpaRepository;
    }

    @Override
    public Newsfeed save(final Newsfeed newsfeed) {
        return newsfeedJpaRepository.save(NewsfeedEntity.from(newsfeed)).toModel();
    }
}
