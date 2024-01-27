package com.example.reservation_purchase.article.infrastructure;

import com.example.reservation_purchase.article.infrastructure.entity.CommentLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeJpaRepository extends JpaRepository<CommentLikeEntity, Long> {
}
