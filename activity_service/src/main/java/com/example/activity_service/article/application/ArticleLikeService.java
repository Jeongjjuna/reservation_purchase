package com.example.activity_service.article.application;

import com.example.activity_service.article.application.port.ArticleLikeRepository;
import com.example.activity_service.article.application.port.ArticleRepository;
import com.example.activity_service.article.domain.Article;
import com.example.activity_service.article.domain.ArticleLike;
import com.example.activity_service.client.CreateNewsfeed;
import com.example.activity_service.client.NewsfeedFeignClient;
import com.example.activity_service.exception.GlobalException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleLikeService {

    private final ArticleRepository articleRepository;
    private final ArticleLikeRepository articleLikeRepository;
    private final NewsfeedFeignClient newsfeedFeignClient;

    public ArticleLikeService(
            final ArticleRepository articleRepository,
            final ArticleLikeRepository articleLikeRepository,
            final NewsfeedFeignClient newsfeedFeignClient
    ) {
        this.articleRepository = articleRepository;
        this.articleLikeRepository = articleLikeRepository;
        this.newsfeedFeignClient = newsfeedFeignClient;
    }

    @Transactional
    public void like(final Long articleId, final Long principalId) {

        Article article = articleRepository.findById(articleId).orElseThrow(() ->
                new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] 게시글이 존재하지 않습니다."));

        ArticleLike articleLike = ArticleLike.builder()
                .article(article)
                .writer(principalId)
                .build();

        ArticleLike saved = articleLikeRepository.save(articleLike);

        /**
         * 뉴스피드에 좋아요 기록 추가
         * TODO : 1. 분산 트랜잭션 체크 2. 테스트할때 mongodb 트랜잭션 체크
         * article.getWriterId(), principalId, "articleLike", saved.getId()
         */
        CreateNewsfeed createNewsfeed = CreateNewsfeed.builder()
                .receiverId(articleLike.getWriter())
                .senderId(principalId)
                .newsfeedType("articleLike")
                .activityId(saved.getId())
                .build();
        newsfeedFeignClient.create(createNewsfeed);
    }
}
