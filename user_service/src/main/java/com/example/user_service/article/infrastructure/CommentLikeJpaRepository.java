package com.example.user_service.article.infrastructure;

import com.example.user_service.article.infrastructure.entity.CommentLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeJpaRepository extends JpaRepository<CommentLikeEntity, Long> {
}
