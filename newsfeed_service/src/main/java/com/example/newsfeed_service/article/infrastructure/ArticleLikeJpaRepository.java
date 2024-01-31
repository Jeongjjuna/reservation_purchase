package com.example.newsfeed_service.article.infrastructure;

import com.example.newsfeed_service.article.infrastructure.entity.ArticleLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleLikeJpaRepository extends JpaRepository<ArticleLikeEntity, Long> {
}
