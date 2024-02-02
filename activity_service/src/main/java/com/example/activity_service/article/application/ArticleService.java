package com.example.activity_service.article.application;

import com.example.activity_service.article.application.port.ArticleRepository;
import com.example.activity_service.article.domain.Article;
import com.example.activity_service.article.domain.ArticleCreate;
import com.example.activity_service.follow.domain.FollowNewsfeed;
import com.example.activity_service.client.NewsfeedClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final NewsfeedClient newsfeedClient;

    public ArticleService(
            final ArticleRepository articleRepository,
            final NewsfeedClient newsfeedClient
    ) {
        this.articleRepository = articleRepository;
        this.newsfeedClient = newsfeedClient;
    }

    @Transactional
    public Long create(Long principalId, ArticleCreate articleCreate) {

        Article article = Article.create(principalId, articleCreate);

        Article saved = articleRepository.save(article);

        /**
         * 뉴스피드에 게시글 작성 기록 추가
         * TODO : 명시적으로 null을 넣지않는 더 좋은 방법이 있을까?
         */
        FollowNewsfeed followNewsfeed = FollowNewsfeed.builder()
                .receiverId(null)
                .senderId(article.getWriterId())
                .newsfeedType("article")
                .activityId(saved.getId())
                .build();
        newsfeedClient.create(followNewsfeed);

        return saved.getId();
    }
}
