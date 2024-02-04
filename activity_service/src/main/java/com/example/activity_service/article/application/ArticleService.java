package com.example.activity_service.article.application;

import com.example.activity_service.article.application.port.ArticleRepository;
import com.example.activity_service.article.domain.Article;
import com.example.activity_service.article.domain.ArticleCreate;
import com.example.activity_service.client.NewsfeedCreate;
import com.example.activity_service.client.NewsfeedFeignClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final NewsfeedFeignClient newsfeedFeignClient;

    public ArticleService(
            final ArticleRepository articleRepository,
            final NewsfeedFeignClient newsfeedFeignClient
    ) {
        this.articleRepository = articleRepository;
        this.newsfeedFeignClient = newsfeedFeignClient;
    }

    @Transactional
    public Long create(Long principalId, ArticleCreate articleCreate) {

        Article article = Article.create(principalId, articleCreate);

        Article saved = articleRepository.save(article);

        /**
         * 뉴스피드에 게시글 작성 기록 추가
         * TODO : 명시적으로 null을 넣지않는 더 좋은 방법이 있을까?
         */
        NewsfeedCreate newsfeedCreate = NewsfeedCreate.builder()
                .receiverId(null)
                .senderId(article.getWriterId())
                .newsfeedType("article")
                .activityId(saved.getId())
                .build();
        newsfeedFeignClient.create(newsfeedCreate);

        return saved.getId();
    }
}
