package com.example.reservation_purchase.follow.infrastructure;

import com.example.reservation_purchase.follow.infrastructure.entity.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface FollowJpaRepository extends JpaRepository<FollowEntity, Long> {

    @Query("SELECT f FROM FollowEntity f JOIN FETCH f.followerMember m WHERE m.id = :followerId AND f.deletedAt IS NULL")
    List<FollowEntity> findByFollowingMember(@Param("followerId") Long followerId);
}