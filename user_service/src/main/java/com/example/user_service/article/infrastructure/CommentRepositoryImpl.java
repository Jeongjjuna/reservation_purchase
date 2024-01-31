package com.example.user_service.article.infrastructure;

import com.example.user_service.article.application.port.CommentRepository;
import com.example.user_service.article.domain.Comment;
import com.example.user_service.article.infrastructure.entity.CommentEntity;
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
