package com.example.activity_service.comment.infrastructure;

import com.example.activity_service.comment.infrastructure.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaEntity extends JpaRepository<CommentEntity, Long> {
}
