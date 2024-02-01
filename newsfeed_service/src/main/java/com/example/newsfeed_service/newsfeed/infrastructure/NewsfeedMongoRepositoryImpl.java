package com.example.newsfeed_service.newsfeed.infrastructure;

import com.example.newsfeed_service.newsfeed.application.port.NewsfeedRepository;
import com.example.newsfeed_service.newsfeed.domain.Newsfeed;
import com.example.newsfeed_service.newsfeed.infrastructure.document.NewsfeedDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class NewsfeedMongoRepositoryImpl implements NewsfeedRepository {

    private final NewsfeedMongoDbRepository newsfeedMongoDbRepository;

    public NewsfeedMongoRepositoryImpl(final NewsfeedMongoDbRepository newsfeedMongoDbRepository) {
        this.newsfeedMongoDbRepository = newsfeedMongoDbRepository;
    }

    @Override
    public Newsfeed save(final Newsfeed newsfeed) {
        return newsfeedMongoDbRepository.save(NewsfeedDocument.from(newsfeed)).toModel();
    }

    @Override
    public Page<Newsfeed> findAllByFollowingIds(final List<Long> followingIds, final Pageable pageable) {
        return newsfeedMongoDbRepository.findAllBySenderIdIn(followingIds, pageable).map(NewsfeedDocument::toModel);
    }
}
