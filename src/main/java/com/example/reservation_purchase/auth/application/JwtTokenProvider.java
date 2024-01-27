package com.example.reservation_purchase.auth.application;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Service
public class JwtTokenProvider {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.expired-time.token.access}")
    private Long accessTokenExpiredTimeMs;

    @Value("${jwt.expired-time.token.refresh}")
    private Long refreshTokenExpiredTimeMs;

    public String generate(String email, String userName, Long expiredTime) {
        Claims claims = Jwts.claims();
        claims.put("userName", userName);
        claims.put("email", email);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredTime))
                .signWith(getKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * TODO : 나중에 람다 형식으로 리팩토링 해보자
     */
    public String generateAccess(String email, String userName) {
        return generate(email, userName, accessTokenExpiredTimeMs);
    }

    public String generateRefresh(String email, String userName) {
        return generate(email, userName, refreshTokenExpiredTimeMs);
    }

    public boolean isExpired(String token) {
        Date expiredDate = extractClaims(token).getExpiration();
        return expiredDate.before(new Date());
    }

    /**
     * 토큰 속 정보 name 추출
     */
    public String getName(String token) {
        return extractClaims(token).get("name", String.class);
    }

    /**
     * 토큰 속 정보 email 추출
     */
    public String getEmail(String token) {
        return extractClaims(token).get("email", String.class);
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey(secretKey))
                .build().parseClaimsJws(token).getBody();
    }

    private Key getKey(String key) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}