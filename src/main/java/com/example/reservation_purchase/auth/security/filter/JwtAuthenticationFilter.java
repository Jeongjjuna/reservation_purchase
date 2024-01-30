package com.example.reservation_purchase.auth.security.filter;

import com.example.reservation_purchase.auth.security.jwt.JwtTokenProvider;
import com.example.reservation_purchase.auth.security.jwt.TokenType;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(final UserDetailsService userDetailsService, final JwtTokenProvider jwtTokenProvider) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

            final String token = parseBearerToken(authorizationHeader);

            String email = jwtTokenProvider.getEmail(token, TokenType.ACCESS);
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    token,
                    userDetails.getAuthorities()
            );
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("jwt 인증 필터를 통과하였습니다.");
        } catch (SecurityException | MalformedJwtException e) {
            log.error("[ERROR] 유효하지 않은 jwt 서명 입니다.");
            request.setAttribute("JwtAuthenticationFilterException", e);
        } catch (ExpiredJwtException e) {
            log.error("[ERROR] 만료된 jwt 토큰 입니다.");
            request.setAttribute("JwtAuthenticationFilterException", e);
        } catch (UnsupportedJwtException e) {
            log.error("[ERROR] 지원되지 않는 jwt 토큰 입니다.");
            request.setAttribute("JwtAuthenticationFilterException", e);
        } catch (IllegalArgumentException e) {
            log.error("[ERROR] 잘못된 jwt 토큰 입니다.");
            request.setAttribute("JwtAuthenticationFilterException", e);
        } catch (NullPointerException e) {
            request.setAttribute("JwtAuthenticationFilterException", e);
            log.info("[INFO] 인증토큰이 없는 요청이 들어왔습니다.");
        } catch (Exception e) {
            request.setAttribute("JwtAuthenticationFilterException", e);
            log.error("[ERROR] 기타 인증 정보가 잘못됐습니다. {}", e.getMessage());
        } finally {
            filterChain.doFilter(request, response);
        }
    }

    private String parseBearerToken(final String header) {
        return header.split(" ")[1].trim();
    }

}
