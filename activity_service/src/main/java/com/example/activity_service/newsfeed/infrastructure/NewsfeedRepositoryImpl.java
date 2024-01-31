package com.example.activity_service.newsfeed.infrastructure;

import com.example.activity_service.newsfeed.domain.Newsfeed;
import com.example.activity_service.newsfeed.application.port.NewsfeedRepository;
import com.example.activity_service.newsfeed.infrastructure.entity.NewsfeedEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.List;

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

    @Override
    public Page<Newsfeed> findAllByFollowingIds(List<Long> followingIds, Pageable pageable) {
        return newsfeedJpaRepository.findAllByFollowingIds(followingIds, pageable).map(NewsfeedEntity::toModel);
    }

}
