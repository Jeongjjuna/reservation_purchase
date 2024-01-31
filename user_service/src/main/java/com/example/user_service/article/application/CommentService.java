package com.example.user_service.article.application;

import com.example.user_service.article.application.port.ArticleRepository;
import com.example.user_service.article.application.port.CommentRepository;
import com.example.user_service.article.domain.Article;
import com.example.user_service.article.domain.Comment;
import com.example.user_service.article.domain.CommentCreate;
import com.example.user_service.exception.GlobalException;
import com.example.user_service.newsfeed.application.NewsfeedService;
import com.example.user_service.newsfeed.domain.NewsfeedCreate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final NewsfeedService newsfeedService;

    public CommentService(
            final CommentRepository commentRepository,
            final ArticleRepository articleRepository,
            final NewsfeedService newsfeedService
    ) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
        this.newsfeedService = newsfeedService;
    }

    @Transactional
    public Long create(final Long principalId, final CommentCreate commentCreate) {

        Article article = articleRepository.findById(commentCreate.getArticleId()).orElseThrow(() ->
                new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] 댓글을 작성할 게시글을 찾을 수 없음"));

        Comment comment = Comment.create(commentCreate, article, principalId);

        Comment saved = commentRepository.save(comment);

        /**
         * 뉴스피드에 댓글 기록 추가
         * principalId -> article.getWriterId()
         * 추후에 서비스로 분리 후 RestTemplate 으로 호출한다.
         */
        NewsfeedCreate newsfeedCreate = NewsfeedCreate.builder()
                .receiverId(article.getWriterId())
                .senderId(principalId)
                .newsfeedType("comment")
                .activityId(saved.getId())
                .build();
        newsfeedService.create(newsfeedCreate);

        return saved.getId();
    }
}
