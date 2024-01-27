package com.example.reservation_purchase.article.application.port;

import com.example.reservation_purchase.article.domain.Comment;

public interface CommentRepository {
    Comment save(Comment comment);
}
