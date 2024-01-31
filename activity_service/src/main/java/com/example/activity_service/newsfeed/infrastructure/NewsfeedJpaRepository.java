package com.example.activity_service.newsfeed.infrastructure;

import com.example.activity_service.newsfeed.infrastructure.entity.NewsfeedEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface NewsfeedJpaRepository extends JpaRepository<NewsfeedEntity, Long> {

    /**
     * Hibernate:
     * select ne1_0.id,ne1_0.created_at,ne1_0.newsfeed_type,ne1_0.receiver_id,ne1_0.sender_id,ne1_0.viewed
     * from newsfeed ne1_0
     * where 1=0
     * order by ne1_0.created_at
     * desc limit ?,?
     */
    @Query("SELECT n FROM NewsfeedEntity n WHERE n.senderId IN :followingIds")
    Page<NewsfeedEntity> findAllByFollowingIds(@Param("followingIds") List<Long> followingIds, Pageable pageable);
}
