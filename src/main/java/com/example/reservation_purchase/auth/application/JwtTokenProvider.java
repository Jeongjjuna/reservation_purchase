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

    @Value("${jwt.accessToken.expired-time-ms}")
    private Long accessTokenExpiredTimeMs;

    public String generate(String email, String userName) {
        Claims claims = Jwts.claims();
        claims.put("userName", userName);
        claims.put("email", email);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiredTimeMs))
                .signWith(getKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUserName(String token) {
        return extractClaims(token).get("name", String.class);
    }

    public String getEmail(String token) {
        return extractClaims(token).get("email", String.class);
    }

    public boolean isExpired(String token) {
        Date expiredDate = extractClaims(token).getExpiration();
        return expiredDate.before(new Date());
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