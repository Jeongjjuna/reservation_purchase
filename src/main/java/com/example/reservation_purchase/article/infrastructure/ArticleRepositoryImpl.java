package com.example.reservation_purchase.article.infrastructure;

import com.example.reservation_purchase.article.application.port.ArticleRepository;
import com.example.reservation_purchase.article.domain.Article;
import com.example.reservation_purchase.article.infrastructure.entity.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository {

    private final ArticleJpaRepository articleJpaRepository;

    public ArticleRepositoryImpl(final ArticleJpaRepository articleJpaRepository) {
        this.articleJpaRepository = articleJpaRepository;
    }

    @Override
    public Article save(final Article article) {
        return articleJpaRepository.save(ArticleEntity.from(article)).toModel();
    }

    @Override
    public Page<Article> findByWriterIdIn(final List<Long> followingIds, final Pageable pageable) {
        return articleJpaRepository.findByWriterIdIn(followingIds, pageable).map(ArticleEntity::toModel);
    }

    @Override
    public Optional<Article> findById(final Long articleId) {
        return articleJpaRepository.findById(articleId).map(ArticleEntity::toModel);
    }
}
