package com.example.newsfeed_service.auth.repository;

import com.example.newsfeed_service.auth.application.port.RedisMailRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import java.time.Duration;

@Repository
public class RedisMailRepositoryImpl implements RedisMailRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisMailRepositoryImpl(final RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void setDataExpire(final String email, final String authenticNumber, final long duration) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(email, authenticNumber, expireDuration);
    }

    @Override
    public String getData(String key) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        return valueOperations.get(key);
    }

    @Override
    public void deleteData(String key) {
        redisTemplate.delete(key);
    }
}
