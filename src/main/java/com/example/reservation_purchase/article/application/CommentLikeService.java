package com.example.reservation_purchase.article.application;

import com.example.reservation_purchase.article.application.port.CommentLikeRepository;
import com.example.reservation_purchase.article.application.port.CommentRepository;
import com.example.reservation_purchase.article.domain.Comment;
import com.example.reservation_purchase.article.domain.CommentLike;
import com.example.reservation_purchase.exception.GlobalException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentLikeService {

    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    public CommentLikeService(final CommentRepository commentRepository, final CommentLikeRepository commentLikeRepository) {
        this.commentRepository = commentRepository;
        this.commentLikeRepository = commentLikeRepository;
    }

    @Transactional
    public void like(final Long commentId, final Long principalId) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] 댓글이 존재하지 않습니다."));

        CommentLike commentLike = CommentLike.builder()
                .comment(comment)
                .writerId(principalId)
                .build();

        commentLikeRepository.save(commentLike);
    }
}
