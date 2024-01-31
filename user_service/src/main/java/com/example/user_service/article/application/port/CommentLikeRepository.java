package com.example.user_service.article.application.port;

import com.example.user_service.article.domain.CommentLike;

public interface CommentLikeRepository {
    CommentLike save(CommentLike commentLike);
}
