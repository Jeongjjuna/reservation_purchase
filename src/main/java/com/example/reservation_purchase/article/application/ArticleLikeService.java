package com.example.reservation_purchase.article.application;

import com.example.reservation_purchase.article.application.port.ArticleLikeRepository;
import com.example.reservation_purchase.article.application.port.ArticleRepository;
import com.example.reservation_purchase.article.domain.Article;
import com.example.reservation_purchase.article.domain.ArticleLike;
import com.example.reservation_purchase.newsfeed.application.NewsfeedService;
import com.example.reservation_purchase.newsfeed.domain.NewsfeedCreate;
import org.springframework.stereotype.Service;

@Service
public class ArticleLikeService {

    private final ArticleRepository articleRepository;
    private final ArticleLikeRepository articleLikeRepository;
    private final NewsfeedService newsfeedService;

    public ArticleLikeService(
            final ArticleRepository articleRepository,
            final ArticleLikeRepository articleLikeRepository,
            final NewsfeedService newsfeedService) {
        this.articleRepository = articleRepository;
        this.articleLikeRepository = articleLikeRepository;
        this.newsfeedService = newsfeedService;
    }

    public void like(final Long articleId, final Long principalId) {

        Article article = articleRepository.findById(articleId).orElseThrow(() ->
                new IllegalArgumentException());

        ArticleLike articleLike = ArticleLike.builder()
                .article(article)
                .memberId(principalId)
                .build();

        ArticleLike saved = articleLikeRepository.save(articleLike);

        /**
         * 뉴스피드에 좋아요 기록 추가
         * 추후에 서비스로 분리 후 RestTemplate 으로 호출한다.
         */
        NewsfeedCreate newsfeedCreate = NewsfeedCreate.builder()
                .receiverId(article.getWriterId())
                .senderId(principalId)
                .newsfeedType("articleLike")
                .activityId(saved.getId())
                .build();
        newsfeedService.create(newsfeedCreate);
    }
}
