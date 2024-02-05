package com.example.activity_service.article.application;

import com.example.activity_service.article.application.port.ArticleRepository;
import com.example.activity_service.article.domain.Article;
import com.example.activity_service.article.domain.ArticleCreate;
import com.example.activity_service.client.NewsfeedCreate;
import com.example.activity_service.client.NewsfeedFeignClient;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.AllArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final NewsfeedFeignClient newsfeedFeignClient;
    private final CircuitBreakerFactory circuitBreakerFactory;
    private final RetryRegistry retryRegistry;

    @Transactional
    public Long create(final Long principalId, final ArticleCreate articleCreate) {

        final Article article = Article.create(principalId, articleCreate);

        final Article saved = articleRepository.save(article);

        final NewsfeedCreate newsfeedCreate = article.toNewsfeedCreate();

        // newsfeed_service 서비스에 뉴스피드 생성 요청(feign client)
        sendNewsfeedRequest(newsfeedCreate); // TODO : f1. 분산 트랜잭션 체크 2. 테스트할때 mongodb 트랜잭션 체크

        return saved.getId();
    }

    private void sendNewsfeedRequest(final NewsfeedCreate newsfeedCreate) {
        final CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        final Retry retry = retryRegistry.retry("retry");
        circuitBreaker.run(() -> Retry.decorateFunction(retry, s -> {
            newsfeedFeignClient.create(newsfeedCreate);
            return "success";
        }).apply(1), throwable -> "failure");
    }
}
