package com.example.activity_service.article.application;

import com.example.activity_service.article.application.port.ArticleLikeRepository;
import com.example.activity_service.article.application.port.ArticleRepository;
import com.example.activity_service.article.domain.Article;
import com.example.activity_service.article.domain.ArticleLike;
import com.example.activity_service.exception.GlobalException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleLikeService {

    private final ArticleRepository articleRepository;
    private final ArticleLikeRepository articleLikeRepository;

    public ArticleLikeService(
            final ArticleRepository articleRepository,
            final ArticleLikeRepository articleLikeRepository
    ) {
        this.articleRepository = articleRepository;
        this.articleLikeRepository = articleLikeRepository;
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
         * TODO : 뉴스피드에 좋아요 이벤트를 기록한다.
         * article.getWriterId(), principalId, "articleLike", saved.getId()
         */
    }
}
