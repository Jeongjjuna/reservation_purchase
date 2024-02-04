package com.example.user_service.auth.security;

import com.example.user_service.common.exception.ErrorResponse;
import com.example.user_service.common.response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public CustomAuthenticationEntryPoint(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        log.error("[ERROR] occurs CustomAuthenticationEntryPoint -> commence()");

        Exception e = (Exception) request.getAttribute("JwtAuthenticationFilterException");
        createResponse(response, e);
    }

    private void createResponse(HttpServletResponse response, final Exception e) throws IOException {
        Response<ErrorResponse> body = Response.error(ErrorResponse.builder()
                .status(HttpStatus.UNAUTHORIZED)
                .message("[ERROR] valid authentication token is required")
                .timestamp(LocalDateTime.now())
                .build()
        );
        response.setContentType("application/json");
        response.setStatus(401);
        response.getWriter().write(objectMapper.writeValueAsString(body));
    }
}