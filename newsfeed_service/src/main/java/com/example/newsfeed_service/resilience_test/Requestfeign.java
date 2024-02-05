package com.example.newsfeed_service.resilience_test;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class Requestfeign {

    private final FeignRequest feignRequest;
    private final CircuitBreakerFactory circuitBreakerFactory;
    private final RetryRegistry retryRegistry;

    public Requestfeign(
            final FeignRequest feignRequest,
            final CircuitBreakerFactory circuitBreakerFactory,
            final RetryRegistry retryRegistry
    ) {
        this.feignRequest = feignRequest;
        this.circuitBreakerFactory = circuitBreakerFactory;
        this.retryRegistry = retryRegistry;
    }

    @GetMapping("/errorful/case1")
    public ResponseEntity<String> case1() {
        // 1. CircuitBreaker 적용
        // CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        // String response = circuitBreaker.run(() -> feignRequest.case1(), throwable -> "대체문자열");

        // 2. Retry 적용
        // Retry retry = retryRegistry.retry("retry");
        // String response = Retry.decorateFunction(retry, s -> feignRequest.case1()).apply(1);

        // 3. CircuitBreaker + Retry 적용
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        Retry retry = retryRegistry.retry("retry");
        String response = circuitBreaker.run(
                () -> Retry.decorateFunction(retry, s -> feignRequest.case1()).apply(1), throwable -> "대체문자열");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/errorful/case2")
    public ResponseEntity<String> case2() {
        // 1. CircuitBreaker 적용
        // CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        // String response = circuitBreaker.run(() -> feignRequest.case2(), throwable -> "대체문자열");

        // 2. Retry 적용
        // Retry retry = retryRegistry.retry("retry");
        // String response = Retry.decorateFunction(retry, s -> feignRequest.case2()).apply(1);

        // 3. CircuitBreaker + Retry 적용
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        Retry retry = retryRegistry.retry("retry");
        String response = circuitBreaker.run(
                () -> Retry.decorateFunction(retry, s -> feignRequest.case2()).apply(1), throwable -> "대체문자열");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/errorful/case3")
    public ResponseEntity<String> case3() {
        // 1. CircuitBreaker 적용
        // CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        // String response = circuitBreaker.run(() -> feignRequest.case3(), throwable -> "대체문자열");

        // 2. Retry 적용
        // Retry retry = retryRegistry.retry("retry");
        // String response = Retry.decorateFunction(retry, s -> feignRequest.case3()).apply(1);

        // 3. CircuitBreaker + Retry 적용
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        Retry retry = retryRegistry.retry("retry");
        String response = circuitBreaker.run(
                () -> Retry.decorateFunction(retry, s -> feignRequest.case3()).apply(1), throwable -> "대체문자열");

        return ResponseEntity.ok(response);
    }
}
