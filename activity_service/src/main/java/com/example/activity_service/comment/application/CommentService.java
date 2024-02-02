package com.example.activity_service.comment.application;

import com.example.activity_service.article.application.port.ArticleRepository;
import com.example.activity_service.article.domain.Article;
import com.example.activity_service.client.CreateNewsfeed;
import com.example.activity_service.client.NewsfeedFeignClient;
import com.example.activity_service.comment.application.port.CommentRepository;
import com.example.activity_service.comment.domain.Comment;
import com.example.activity_service.comment.domain.CommentCreate;
import com.example.activity_service.exception.GlobalException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final NewsfeedFeignClient newsfeedFeignClient;

    public CommentService(
            final CommentRepository commentRepository,
            final ArticleRepository articleRepository,
            final NewsfeedFeignClient newsfeedFeignClient
    ) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
        this.newsfeedFeignClient = newsfeedFeignClient;
    }

    @Transactional
    public Long create(final Long principalId, final CommentCreate commentCreate) {

        Article article = articleRepository.findById(commentCreate.getArticleId()).orElseThrow(() ->
                new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] 댓글을 작성할 게시글을 찾을 수 없음"));

        Comment comment = Comment.create(commentCreate, article, principalId);

        Comment saved = commentRepository.save(comment);


        /**
         * 뉴스피드에 댓글 기록 추가
         * TODO : 1. 분산 트랜잭션 체크 2. 테스트할때 mongodb 트랜잭션 체크
         * article.getWriterId(), principalId, "comment", saved.getId()
         */
        CreateNewsfeed createNewsfeed = CreateNewsfeed.builder()
                .receiverId(comment.getWriterId())
                .senderId(principalId)
                .newsfeedType("comment")
                .activityId(saved.getId())
                .build();
        newsfeedFeignClient.create(createNewsfeed);

        return saved.getId();
    }
}
