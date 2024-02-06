package com.example.apigateway_service.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    public LoggingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(final Config config) {
        return (exchange, chain) -> {
            final ServerHttpRequest request = exchange.getRequest();
            final ServerHttpResponse response = exchange.getResponse();

            log.info("API Gateway 요청 필터 : request uri -> {}", request.getURI());

            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("API Gateway 응답 필터 : response status -> {}", response.getStatusCode());
            }));
        };
    }

    public static class Config {
        // 설정 정보가 있으면 여기다가 넣어준다.
    }

}
