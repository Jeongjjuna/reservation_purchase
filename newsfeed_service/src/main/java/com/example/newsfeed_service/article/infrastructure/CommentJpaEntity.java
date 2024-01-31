package com.example.newsfeed_service.article.infrastructure;

import com.example.newsfeed_service.article.infrastructure.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaEntity extends JpaRepository<CommentEntity, Long> {
}
