package com.example.user_service.article.application.port;

import com.example.user_service.article.domain.Comment;
import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment comment);

    Optional<Comment> findById(Long commentId);
}
