package com.example.activity_service.comment.infrastructure;

import com.example.activity_service.comment.application.port.CommentRepository;
import com.example.activity_service.comment.domain.Comment;
import com.example.activity_service.comment.infrastructure.entity.CommentEntity;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaEntity commentJpaEntity;

    public CommentRepositoryImpl(final CommentJpaEntity commentJpaEntity) {
        this.commentJpaEntity = commentJpaEntity;
    }

    @Override
    public Comment save(final Comment comment) {
        return commentJpaEntity.save(CommentEntity.from(comment)).toModel();
    }

    @Override
    public Optional<Comment> findById(final Long commentId) {
        return commentJpaEntity.findById(commentId).map(CommentEntity::toModel);
    }
}
