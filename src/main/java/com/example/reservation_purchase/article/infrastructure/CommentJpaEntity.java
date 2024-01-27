package com.example.reservation_purchase.article.infrastructure;

import com.example.reservation_purchase.article.infrastructure.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaEntity extends JpaRepository<CommentEntity, Long> {
}
