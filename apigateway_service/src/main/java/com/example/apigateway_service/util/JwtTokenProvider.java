package com.example.apigateway_service.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtTokenProvider {

    @Value("${jwt.secret-key.access}")
    private String accessSecretKey;

    public boolean isExpired(final String token, final TokenType type) {
        final Date expiredDate = extractClaims(token, type).getExpiration();
        return expiredDate.before(new Date());
    }

    public Long getMemberId(final String token, final TokenType type) {
        return extractClaims(token, type).get("memberId", Long.class);
    }

    private Claims extractClaims(final String token, final TokenType type) {
        return Jwts.parserBuilder().setSigningKey(getKey(accessSecretKey))
                .build().parseClaimsJws(token).getBody();
    }

    private Key getKey(final String key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}