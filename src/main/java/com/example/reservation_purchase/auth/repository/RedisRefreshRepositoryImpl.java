package com.example.reservation_purchase.auth.repository;

import com.example.reservation_purchase.auth.application.port.RefreshRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import java.time.Duration;

@Repository
public class RedisRefreshRepositoryImpl implements RefreshRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisRefreshRepositoryImpl(final RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public String save(final String refreshToken, final Long memberId, final long duration) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(refreshToken, String.valueOf(memberId), expireDuration);
        return refreshToken;
    }

    @Override
    public String findByValue(final String value) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(value);
    }

    @Override
    public void delete(final String value) {
        redisTemplate.delete(value);
    }
}
