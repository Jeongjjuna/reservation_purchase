package com.example.newsfeed_service.follow.infrastructure.entity;

import com.example.newsfeed_service.follow.domain.Follow;
import com.example.newsfeed_service.member.infrastructure.entity.MemberEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_member_id", nullable = false, columnDefinition = "팔로우를 한 사람")
    private MemberEntity followerMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_member_id", nullable = false, columnDefinition = "팔로우를 당한사람")
    private MemberEntity followingMember;

    @Column(name = "follow_check")
    private boolean followCheck;

    @LastModifiedDate
    @Column(name = "updated_at")
    protected LocalDateTime updatedAt;

    public static FollowEntity from(Follow follow) {
        FollowEntity followEntity = new FollowEntity();
        followEntity.id = follow.getId();
        followEntity.followerMember = MemberEntity.from(follow.getFollowerMember());
        followEntity.followingMember = MemberEntity.from(follow.getFollowingMember());
        followEntity.followCheck = follow.isFollowCheck();
        followEntity.updatedAt = follow.getUpdatedAt();
        return followEntity;
    }

    public Follow toModel() {
        return Follow.builder()
                .id(id)
                .followerMember(followerMember.toModel())
                .followingMember(followingMember.toModel())
                .followCheck(followCheck)
                .updatedAt(updatedAt)
                .build();
    }
}
