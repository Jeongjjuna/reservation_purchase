package com.example.activity_service.comment.infrastructure;

import com.example.activity_service.comment.application.port.CommentLikeRepository;
import com.example.activity_service.comment.domain.CommentLike;
import com.example.activity_service.comment.infrastructure.entity.CommentLikeEntity;
import org.springframework.stereotype.Repository;

@Repository
public class CommentLikeRepositoryImpl implements CommentLikeRepository {

    private final CommentLikeJpaRepository commentLikeJpaRepository;

    public CommentLikeRepositoryImpl(final CommentLikeJpaRepository commentLikeJpaRepository) {
        this.commentLikeJpaRepository = commentLikeJpaRepository;
    }

    @Override
    public CommentLike save(CommentLike commentLike) {
        return commentLikeJpaRepository.save(CommentLikeEntity.from(commentLike)).toModel();
    }
}
