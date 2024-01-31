package com.example.newsfeed_service.auth.repository;

import com.example.newsfeed_service.auth.application.port.RedisRefreshRepository;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import java.time.Duration;
import java.util.Map;

@Repository
public class RedisRedisRefreshRepositoryImpl implements RedisRefreshRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private final HashOperations<String, String, String> hashOperations;
    private final ValueOperations<String, String> valueOperations;

    public RedisRedisRefreshRepositoryImpl(final RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = redisTemplate.opsForValue();
        this.hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public String save(final String key, final String value, final long duration) {
        Duration expireDuration = Duration.ofSeconds(duration);
        valueOperations.set(key, String.valueOf(value), expireDuration);
        return key;
    }

    @Override
    public String findByValue(final String key) {
        return valueOperations.get(key);
    }

    @Override
    public void delete(final String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void addToHash(final String hashName, final String key, final String value) {
        hashOperations.put(hashName, key, value);
    }

    @Override
    public String getFromHash(final String hashName, final String key) {
        return hashOperations.get(hashName, key);
    }

    @Override
    public Map<String, String> getAllFromHash(final String hashName) {
        return hashOperations.entries(hashName);
    }

    @Override
    public void removeFromHash(final String hashName, final String key) {
        hashOperations.delete(hashName, key);
    }

    @Override
    public void removeHashName(final String hashName) {
        hashOperations.delete(hashName);
    }
}
