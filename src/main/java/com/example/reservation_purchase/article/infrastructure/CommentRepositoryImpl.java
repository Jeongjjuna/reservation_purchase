package com.example.reservation_purchase.article.infrastructure;

import com.example.reservation_purchase.article.application.port.CommentRepository;
import com.example.reservation_purchase.article.domain.Comment;
import com.example.reservation_purchase.article.infrastructure.entity.CommentEntity;
import org.springframework.stereotype.Repository;

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
}
