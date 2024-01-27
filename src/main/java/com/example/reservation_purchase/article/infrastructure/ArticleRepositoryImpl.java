package com.example.reservation_purchase.article.infrastructure;

import com.example.reservation_purchase.article.application.port.ArticleRepository;
import com.example.reservation_purchase.article.domain.Article;
import com.example.reservation_purchase.article.infrastructure.entity.ArticleEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository {

    public ArticleRepositoryImpl(final ArticleJpaRepository articleJpaRepository) {
        this.articleJpaRepository = articleJpaRepository;
    }

    private final ArticleJpaRepository articleJpaRepository;

    @Override
    public Article save(final Article article) {
        return articleJpaRepository.save(ArticleEntity.from(article)).toModel();
    }
}
