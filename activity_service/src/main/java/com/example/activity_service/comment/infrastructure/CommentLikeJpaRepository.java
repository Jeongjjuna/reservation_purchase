package com.example.activity_service.comment.infrastructure;

import com.example.activity_service.comment.infrastructure.entity.CommentLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeJpaRepository extends JpaRepository<CommentLikeEntity, Long> {
}
