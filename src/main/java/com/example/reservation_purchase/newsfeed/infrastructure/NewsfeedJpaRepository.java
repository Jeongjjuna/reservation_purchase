package com.example.reservation_purchase.newsfeed.infrastructure;

import com.example.reservation_purchase.newsfeed.infrastructure.entity.NewsfeedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsfeedJpaRepository extends JpaRepository<NewsfeedEntity, Long> {
}
