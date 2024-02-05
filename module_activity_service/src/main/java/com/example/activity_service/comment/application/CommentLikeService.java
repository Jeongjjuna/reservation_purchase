package com.example.activity_service.comment.application;

import com.example.activity_service.comment.application.port.CommentLikeRepository;
import com.example.activity_service.comment.application.port.CommentRepository;
import com.example.activity_service.comment.domain.CommentLike;
import com.example.activity_service.common.exception.GlobalException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class CommentLikeService {

    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public void like(final Long commentId, final Long principalId) {
        commentRepository.findById(commentId)
                .map(comment -> CommentLike.create(comment, principalId))
                .map(commentLikeRepository::save)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] 댓글이 존재하지 않습니다."));
    }
}
