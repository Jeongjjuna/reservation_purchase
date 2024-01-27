package com.example.reservation_purchase.article.infrastructure;

import com.example.reservation_purchase.article.application.port.ArticleLikeRepository;
import com.example.reservation_purchase.article.domain.ArticleLike;
import com.example.reservation_purchase.article.infrastructure.entity.ArticleLikeEntity;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleLikeRepositoryImpl implements ArticleLikeRepository {

    private final ArticleLikeJpaRepository articleLikeJpaRepository;

    public ArticleLikeRepositoryImpl(final ArticleLikeJpaRepository articleLikeJpaRepository) {
        this.articleLikeJpaRepository = articleLikeJpaRepository;
    }

    @Override
    public ArticleLike save(final ArticleLike articleLike) {
        return articleLikeJpaRepository.save(ArticleLikeEntity.from(articleLike)).toModel();
    }
}
