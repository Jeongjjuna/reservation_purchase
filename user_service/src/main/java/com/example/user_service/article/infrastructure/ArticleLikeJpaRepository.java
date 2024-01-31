package com.example.user_service.article.infrastructure;

import com.example.user_service.article.infrastructure.entity.ArticleLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleLikeJpaRepository extends JpaRepository<ArticleLikeEntity, Long> {
}
