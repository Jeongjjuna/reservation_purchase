package com.example.user_service.article.infrastructure;

import com.example.user_service.article.application.port.ArticleLikeRepository;
import com.example.user_service.article.domain.ArticleLike;
import com.example.user_service.article.infrastructure.entity.ArticleLikeEntity;
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
