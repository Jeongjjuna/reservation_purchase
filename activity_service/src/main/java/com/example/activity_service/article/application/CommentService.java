package com.example.activity_service.article.application;

import com.example.activity_service.article.application.port.ArticleRepository;
import com.example.activity_service.article.application.port.CommentRepository;
import com.example.activity_service.article.domain.Article;
import com.example.activity_service.article.domain.Comment;
import com.example.activity_service.article.domain.CommentCreate;
import com.example.activity_service.exception.GlobalException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    public CommentService(
            final CommentRepository commentRepository,
            final ArticleRepository articleRepository
    ) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
    }

    @Transactional
    public Long create(final Long principalId, final CommentCreate commentCreate) {

        Article article = articleRepository.findById(commentCreate.getArticleId()).orElseThrow(() ->
                new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] 댓글을 작성할 게시글을 찾을 수 없음"));

        Comment comment = Comment.create(commentCreate, article, principalId);

        Comment saved = commentRepository.save(comment);


        /**
         * 뉴스피드에 댓글 기록 추가
         * TODO : 뉴스피드에 좋아요 이벤트를 기록한다.
         * article.getWriterId(), principalId, "comment", saved.getId()
         */

        return saved.getId();
    }
}
