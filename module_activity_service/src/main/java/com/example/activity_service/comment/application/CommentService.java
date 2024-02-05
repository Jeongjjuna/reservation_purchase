package com.example.activity_service.comment.application;

import com.example.activity_service.article.application.port.ArticleRepository;
import com.example.activity_service.client.NewsfeedCreate;
import com.example.activity_service.client.NewsfeedFeignClient;
import com.example.activity_service.comment.application.port.CommentRepository;
import com.example.activity_service.comment.domain.Comment;
import com.example.activity_service.comment.domain.CommentCreate;
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
public class CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final NewsfeedFeignClient newsfeedFeignClient;
    private final CircuitBreakerFactory circuitBreakerFactory;
    private final RetryRegistry retryRegistry;

    @Transactional
    public Long create(final Long principalId, final CommentCreate commentCreate) {

        final Comment saved = articleRepository.findById(commentCreate.getArticleId())
                .map(article -> Comment.create(commentCreate, article, principalId))
                .map(commentRepository::save)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] 댓글을 작성할 게시글을 찾을 수 없음"));


        // newsfeed_service 서비스에 댓글 뉴스피드 생성 요청(feign client)
        final NewsfeedCreate newsfeedCreate = saved.toNewsfeedCreate(); // TODO : 1. 분산 트랜잭션 체크 2. 테스트할때 mongodb 트랜잭션 체크

        sendNewsfeedRequest(newsfeedCreate);

        return saved.getId();
    }

    /**
     * TODO : 서킷브레이커, 리트라이의 공통 부분을 어떻게 리팩토링 해볼지 고민해보자.
     */
    private void sendNewsfeedRequest(final NewsfeedCreate newsfeedCreate) {
        final CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        final Retry retry = retryRegistry.retry("retry");
        circuitBreaker.run(() -> Retry.decorateFunction(retry, s -> {
            newsfeedFeignClient.create(newsfeedCreate);
            return "success";
        }).apply(1), throwable -> "failure");
    }
}
