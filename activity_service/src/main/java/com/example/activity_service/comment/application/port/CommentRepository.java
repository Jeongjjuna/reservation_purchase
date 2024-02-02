package com.example.activity_service.comment.application.port;

import com.example.activity_service.comment.domain.Comment;
import java.util.Optional;

public interface CommentRepository {
    Comment save(Comment comment);

    Optional<Comment> findById(Long commentId);
}
