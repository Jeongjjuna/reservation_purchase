package com.example.reservation_purchase.article.application.port;

import com.example.reservation_purchase.article.domain.CommentLike;

public interface CommentLikeRepository {
    CommentLike save(CommentLike commentLike);
}
