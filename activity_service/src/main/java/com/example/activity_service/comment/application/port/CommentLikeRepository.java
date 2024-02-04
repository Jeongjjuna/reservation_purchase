package com.example.activity_service.comment.application.port;

import com.example.activity_service.comment.domain.CommentLike;

public interface CommentLikeRepository {
    CommentLike save(CommentLike commentLike);
}
