package com.example.activity_service.article.application;

import com.example.activity_service.article.application.port.ArticleLikeRepository;
import com.example.activity_service.article.application.port.ArticleRepository;
import com.example.activity_service.article.domain.ArticleLike;
import com.example.activity_service.client.NewsfeedCreate;
import com.example.activity_service.client.NewsfeedFeignClient;
import com.example.activity_service.common.exception.GlobalException;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.AllArgsConstructor;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class ArticleLikeService {

    private final ArticleRepository articleRepository;
    private final ArticleLikeRepository articleLikeRepository;
    private final NewsfeedFeignClient newsfeedFeignClient;
    private final CircuitBreakerFactory circuitBreakerFactory;
    private final RetryRegistry retryRegistry;

    @Transactional
    public void like(final Long articleId, final Long principalId) {

        ArticleLike saved = articleRepository.findById(articleId)
                .map(article -> ArticleLike.create(article, principalId))
                .map(articleLikeRepository::save)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] 게시글이 존재하지 않습니다."));

        NewsfeedCreate newsfeedCreate = saved.toNewsfeedCreate();

        // newsfeed_service 서비스에 뉴스피드 생성 요청(feign client)
        sendNewsfeedRequest(newsfeedCreate); // TODO : f1. 분산 트랜잭션 체크 2. 테스트할때 mongodb 트랜잭션 체크

    }

    /**
     * TODO : 서킷브레이커, 리트라이의 공통 부분을 어떻게 리팩토링 해볼지 고민해보자.
     */
    private void sendNewsfeedRequest(NewsfeedCreate newsfeedCreate) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        Retry retry = retryRegistry.retry("retry");
        circuitBreaker.run(() -> Retry.decorateFunction(retry, s -> {
            newsfeedFeignClient.create(newsfeedCreate);
            return "success";
        }).apply(1), throwable -> "failure");
    }

}
