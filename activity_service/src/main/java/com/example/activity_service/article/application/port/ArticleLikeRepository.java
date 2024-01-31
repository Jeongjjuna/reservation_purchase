package com.example.activity_service.article.application.port;

import com.example.activity_service.article.domain.ArticleLike;

public interface ArticleLikeRepository {
    ArticleLike save(ArticleLike articleLike);
}
