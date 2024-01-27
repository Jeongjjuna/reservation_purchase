package com.example.reservation_purchase.article.application.port;

import com.example.reservation_purchase.article.domain.Comment;
import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment comment);

    Optional<Comment> findById(Long commentId);
}
