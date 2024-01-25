package com.example.reservation_purchase.follow.infrastructure;

import com.example.reservation_purchase.follow.infrastructure.entity.FollowEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowJpaRepository extends JpaRepository<FollowEntity, Long> {
}