package com.example.user_service.article.application;

import com.example.user_service.article.application.port.ArticleLikeRepository;
import com.example.user_service.article.application.port.ArticleRepository;
import com.example.user_service.article.domain.Article;
import com.example.user_service.article.domain.ArticleLike;
import com.example.user_service.exception.GlobalException;
import com.example.user_service.newsfeed.application.NewsfeedService;
import com.example.user_service.newsfeed.domain.NewsfeedCreate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleLikeService {

    private final ArticleRepository articleRepository;
    private final ArticleLikeRepository articleLikeRepository;
    private final NewsfeedService newsfeedService;

    public ArticleLikeService(
            final ArticleRepository articleRepository,
            final ArticleLikeRepository articleLikeRepository,
            final NewsfeedService newsfeedService
    ) {
        this.articleRepository = articleRepository;
        this.articleLikeRepository = articleLikeRepository;
        this.newsfeedService = newsfeedService;
    }

    @Transactional
    public void like(final Long articleId, final Long principalId) {

        Article article = articleRepository.findById(articleId).orElseThrow(() ->
                new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] 게시글이 존재하지 않습니다."));

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
