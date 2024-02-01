package com.example.newsfeed_service.newsfeed.infrastructure.document;

import com.example.newsfeed_service.newsfeed.domain.Newsfeed;
import com.example.newsfeed_service.newsfeed.domain.NewsfeedType;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@NoArgsConstructor
@Document(collection = "newsfeed")
public class NewsfeedDocument {

    @Id
    private String id;
    private Long receiverId;
    private Long senderId;
    private Long activityId;
    private NewsfeedType newsfeedType;
    private LocalDateTime createdAt;

    public static NewsfeedDocument from(final Newsfeed newsfeed) {
        NewsfeedDocument newsfeedDocument = new NewsfeedDocument();
        newsfeedDocument.id = newsfeed.getId();
        newsfeedDocument.receiverId = newsfeed.getReceiverId();
        newsfeedDocument.senderId = newsfeed.getSenderId();
        newsfeedDocument.activityId = newsfeed.getActivityId();
        newsfeedDocument.newsfeedType = newsfeed.getNewsfeedType();
        newsfeedDocument.createdAt = newsfeed.getCreatedAt();
        return newsfeedDocument;
    }

    public Newsfeed toModel() {
        return Newsfeed.builder()
                .id(id)
                .receiverId(receiverId)
                .senderId(senderId)
                .activityId(activityId)
                .newsfeedType(newsfeedType)
                .createdAt(createdAt)
                .build();
    }
}
