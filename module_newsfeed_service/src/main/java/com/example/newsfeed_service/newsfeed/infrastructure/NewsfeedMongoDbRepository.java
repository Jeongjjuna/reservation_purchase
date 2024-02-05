package com.example.newsfeed_service.newsfeed.infrastructure;

import com.example.newsfeed_service.newsfeed.infrastructure.document.NewsfeedDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface NewsfeedMongoDbRepository extends MongoRepository<NewsfeedDocument, String> {
    Page<NewsfeedDocument> findAllBySenderIdIn(List<Long> senderIds, Pageable pageable);
}
