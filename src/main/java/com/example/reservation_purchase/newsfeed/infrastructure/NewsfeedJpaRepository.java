package com.example.reservation_purchase.newsfeed.infrastructure;

import com.example.reservation_purchase.newsfeed.infrastructure.entity.NewsfeedEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface NewsfeedJpaRepository extends JpaRepository<NewsfeedEntity, Long> {

    @Query("SELECT n FROM NewsfeedEntity n WHERE n.senderId IN :followingIds")
    Page<NewsfeedEntity> findAllByFollowingIds(@Param("followingIds") List<Long> followingIds, Pageable pageable);
}
