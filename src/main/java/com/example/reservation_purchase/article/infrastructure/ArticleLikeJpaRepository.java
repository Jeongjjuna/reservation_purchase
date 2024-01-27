package com.example.reservation_purchase.article.infrastructure;

import com.example.reservation_purchase.article.infrastructure.entity.ArticleLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleLikeJpaRepository extends JpaRepository<ArticleLikeEntity, Long> {
}
