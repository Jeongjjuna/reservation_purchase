package com.example.reservation_purchase.newsfeed.infrastructure.entity;

import com.example.reservation_purchase.newsfeed.domain.Newsfeed;
import com.example.reservation_purchase.newsfeed.domain.NewsfeedType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@Entity
@Table(name = "newsfeed")
public class NewsfeedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "newsfeed_id", updatable = false)
    private Long id;

    @Column(name = "receiver_id", nullable = false)
    private Long receiverId;

    @Column(name = "sender_id", nullable = false)
    private Long senderId;

    @Column(name = "activity_id", nullable = false)
    private Long activityId;

    @Enumerated(EnumType.STRING)
    @Column(name = "newsfeed_type", nullable = false)
    private NewsfeedType newsfeedType;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public static NewsfeedEntity from(final Newsfeed newsfeed) {
        NewsfeedEntity newsfeedEntity = new NewsfeedEntity();
        newsfeedEntity.id = newsfeed.getId();
        newsfeedEntity.receiverId = newsfeed.getReceiverId();
        newsfeedEntity.senderId = newsfeed.getSenderId();
        newsfeedEntity.activityId = newsfeed.getActivityId();
        newsfeedEntity.newsfeedType = newsfeed.getNewsfeedType();
        newsfeedEntity.createdAt = newsfeed.getCreatedAt();
        return newsfeedEntity;
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
