package com.example.activity_service.follow.infrastructure;

import com.example.activity_service.follow.infrastructure.entity.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface FollowJpaRepository extends JpaRepository<FollowEntity, Long> {

    @Query("SELECT f FROM FollowEntity f WHERE f.followerId = :myId")
    List<FollowEntity> findFollowing(@Param("myId") Long myId);

    @Query("SELECT f FROM FollowEntity f WHERE f.followingId = :myId")
    List<FollowEntity> findFollower(@Param("myId") Long myId);

    @Query("SELECT f FROM FollowEntity f WHERE f.followerId = :followerId AND f.followingId = :followingId")
    Optional<FollowEntity> findByFollowerAndFollowing(
            @Param("followerId") Long followerMemberId,
            @Param("followingId") Long followingMemberId
    );
}