package com.example.user_service.article.application.port;

import com.example.user_service.article.domain.ArticleLike;

public interface ArticleLikeRepository {
    ArticleLike save(ArticleLike articleLike);
}
