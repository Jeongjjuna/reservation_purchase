package com.example.activity_service.follow.infrastructure.entity;

import com.example.activity_service.follow.domain.Follow;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@Entity
@Table(name = "follow")
public class FollowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id", updatable = false)
    private Long id;

    @Column(name = "follower_member_id", nullable = false, columnDefinition = "팔로우를 한 사람")
    private Long followerId;

    @Column(name = "following_member_id", nullable = false, columnDefinition = "팔로우를 당한사람")
    private Long followingId;

    @Column(name = "follow_check")
    private boolean followCheck;

    @LastModifiedDate
    @Column(name = "updated_at")
    protected LocalDateTime updatedAt;

    public static FollowEntity from(Follow follow) {
        FollowEntity followEntity = new FollowEntity();
        followEntity.id = follow.getId();
        followEntity.followerId = follow.getFollowerMemberId();
        followEntity.followingId = follow.getFollowingMemberId();
        followEntity.followCheck = follow.isFollowCheck();
        followEntity.updatedAt = follow.getUpdatedAt();
        return followEntity;
    }

    public Follow toModel() {
        return Follow.builder()
                .id(id)
                .followerMemberId(followerId)
                .followingMemberId(followingId)
                .followCheck(followCheck)
                .updatedAt(updatedAt)
                .build();
    }
}
