package com.example.apigateway_service.filter;

import com.example.apigateway_service.response.ErrorResponse;
import com.example.apigateway_service.response.Response;
import com.example.apigateway_service.util.JwtTokenProvider;
import com.example.apigateway_service.util.TokenType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import java.net.URI;
import java.time.LocalDateTime;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.AuthenticationConfig>{

    private final ObjectMapper objectMapper;
    private final JwtTokenProvider jwtTokenProvider;

    public static class AuthenticationConfig {
    }

    public AuthenticationFilter(
            final ObjectMapper objectMapper,
            final JwtTokenProvider jwtTokenProvider
    ) {
        super(AuthenticationConfig.class);
        this.objectMapper = objectMapper;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public GatewayFilter apply(final AuthenticationConfig config) {
        return (exchange, chain) -> {
            log.info("Authorization 인증 필터");
            final ServerHttpRequest request = exchange.getRequest();

            try {
                // Authorization 헤더가 존재하는지 체크
                if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    return onErrorResponse(exchange, "[ERROR] Not found authorization header");
                }

                final String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                final String token = parseBearerToken(authorizationHeader);

                if (jwtTokenProvider.isExpired(token, TokenType.ACCESS)) {
                    return onErrorResponse(exchange, "[ERROR] Invalid jwt token");
                }

                /**
                 *  쿼리파라미터를 추가한 새로운 URI, 새로운 ServerHttpRequest 에 담아서 다음 필터로 넘겨준다.
                 *  ?member=127 (인증회원 id)
                 */
                final Long principalId = jwtTokenProvider.getMemberId(token, TokenType.ACCESS);
                final URI newUri = addParam(request.getURI(), "member", principalId);
                final ServerHttpRequest modifiedRequest = exchange.getRequest().mutate().uri(newUri).build();
                return chain.filter(exchange.mutate().request(modifiedRequest).build());

            } catch (Exception e) {
                log.error(e.getMessage());
                return onErrorResponse(exchange, "[ERROR] Invalid jwt token");
            }
        };
    }

    private Mono<Void> onErrorResponse(final ServerWebExchange exchange, final String message) {
        log.error(message);
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.OK);
        String body = createResponseBody();
        return response.writeWith(Mono.just(response.bufferFactory().wrap(body.getBytes())));
    }

    private URI addParam(URI uri, String name, Long value) {
        return UriComponentsBuilder.fromUri(uri)
                .queryParam(name, value)
                .build(true)
                .toUri();
    }

    private String createResponseBody() {
        final Response<ErrorResponse> body = Response.error(ErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message("[ERROR] valid authentication token is required")
                .timestamp(LocalDateTime.now())
                .build()
        );

        String defaultValue = "[ERROR] Api gateway unauthorized error";
        try{
            return objectMapper.writeValueAsString(body);
        }catch (Exception e) {
            return defaultValue;
        }
    }

    private String parseBearerToken(final String header) {
        return header.split(" ")[1].trim();
    }

}
