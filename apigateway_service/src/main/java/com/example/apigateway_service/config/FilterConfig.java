package com.example.apigateway_service.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

// @Configuration
public class FilterConfig {

    // @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                // user_service
                .route(r -> r.path("/v1/members/**")
                        .uri("http://localhost:8080/"))
                .route(r -> r.path("/v1/auth/**")
                        .uri("http://localhost:8080/"))
                // activity_service
                .route(r -> r.path("/v1/articles/**")
                        .uri("http://localhost:8081/"))
                .route(r -> r.path("/v1/comments/**")
                        .uri("http://localhost:8081/"))
                .route(r -> r.path("/v1/follows/**")
                        .uri("http://localhost:8081/"))
                // newsfeed_service
                .route(r -> r.path("/v1/newsfeeds/**")
                        .uri("http://localhost:8082/"))
                .build();
    }
}
