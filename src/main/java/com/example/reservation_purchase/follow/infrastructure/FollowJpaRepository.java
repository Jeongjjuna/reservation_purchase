package com.example.reservation_purchase.follow.infrastructure;

import com.example.reservation_purchase.follow.infrastructure.entity.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface FollowJpaRepository extends JpaRepository<FollowEntity, Long> {

    /**
     * Hibernate: select fe1_0.id,fe1_0.deleted_at,fm1_0.id,fm1_0.created_at,fm1_0.email,fm1_0.greetings,fm1_0.name,fm1_0.password,fm1_0.profile_url,fm1_0.updated_at,fe1_0.following_member_id
     * from follow fe1_0
     * join member fm1_0 on fm1_0.id=fe1_0.follower_member_id
     * where
     *      fm1_0.id=?
     * and
     *      fe1_0.deleted_at is null
     */
    @Query("SELECT f FROM FollowEntity f JOIN FETCH f.followingMember m WHERE f.followerMember.id = :followerId AND f.deletedAt IS NULL")
    List<FollowEntity> findByFollowingMember(@Param("followerId") Long followerId);

    /**
     * Hibernate:
     * select fe1_0.id,fe1_0.deleted_at,fe1_0.follower_member_id,fe1_0.following_member_id
     * from follow fe1_0
     * where
     *      fe1_0.follower_member_id=?
     * and
     *      fe1_0.following_member_id=?
     */
    @Query("SELECT f FROM FollowEntity f WHERE f.followerMember.id = :followerId AND f.followingMember.id = :followingId")
    Optional<FollowEntity> findByFollowerAndFollowing(
            @Param("followerId") Long followerMemberId,
            @Param("followingId") Long followingMemberId
    );
}