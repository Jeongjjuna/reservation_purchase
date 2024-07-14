package org.example.order_service_v2.infrastructure.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RedisRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Long decr(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }

    public boolean setnx(String key, String value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

}
