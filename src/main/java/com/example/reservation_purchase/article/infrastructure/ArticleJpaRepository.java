package com.example.reservation_purchase.article.infrastructure;

import com.example.reservation_purchase.article.infrastructure.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleJpaRepository extends JpaRepository<ArticleEntity, Long> {
}
