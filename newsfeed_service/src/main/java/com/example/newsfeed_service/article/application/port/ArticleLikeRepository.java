package com.example.newsfeed_service.article.application.port;

import com.example.newsfeed_service.article.domain.ArticleLike;

public interface ArticleLikeRepository {
    ArticleLike save(ArticleLike articleLike);
}
