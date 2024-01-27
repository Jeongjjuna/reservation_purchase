package com.example.reservation_purchase.article.application.port;

import com.example.reservation_purchase.article.domain.ArticleLike;

public interface ArticleLikeRepository {
    ArticleLike save(ArticleLike articleLike);
}
