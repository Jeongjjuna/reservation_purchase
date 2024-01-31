package com.example.newsfeed_service.article.application.port;

import com.example.newsfeed_service.article.domain.CommentLike;

public interface CommentLikeRepository {
    CommentLike save(CommentLike commentLike);
}
