package com.example.newsfeed_service.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Duration;

@Configuration
public class Resilience4JConfig {

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> globalCustomConfiguration() {
        final CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(4) // CircuitBreaker 열지 결정하는 실패 비율. default 50%
                .waitDurationInOpenState(Duration.ofMillis(1000)) // CircuitBreaker 오픈한 상태 유지시간. default 60초
                .slidingWindowType(SlidingWindowType.COUNT_BASED) // 지금까지 호출결과를 카운트로 기록할 것인가, 시간으로 기록할 것인가
                .slidingWindowSize(2)
                .build();

        final TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(4)) // 실패라고 판단되는 시간 제한을 걸어버린다. default 1ch
                .build();

        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .timeLimiterConfig(timeLimiterConfig)
                .circuitBreakerConfig(circuitBreakerConfig)
                .build()
        );
    }

    @Bean
    public RetryRegistry retryConfiguration() {
        final RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(2)
                .build();
        return RetryRegistry.of(retryConfig);
    }

}
