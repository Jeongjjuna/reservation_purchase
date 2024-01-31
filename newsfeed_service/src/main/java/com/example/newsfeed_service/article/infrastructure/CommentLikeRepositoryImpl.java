package com.example.newsfeed_service.article.infrastructure;

import com.example.newsfeed_service.article.application.port.CommentLikeRepository;
import com.example.newsfeed_service.article.domain.CommentLike;
import com.example.newsfeed_service.article.infrastructure.entity.CommentLikeEntity;
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
