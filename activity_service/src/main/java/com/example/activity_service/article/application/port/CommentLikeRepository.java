package com.example.activity_service.article.application.port;

import com.example.activity_service.article.domain.CommentLike;

public interface CommentLikeRepository {
    CommentLike save(CommentLike commentLike);
}
