package com.example.module_stock_service.stock.infrastructure.repository;

import com.example.module_stock_service.stock.domain.Stock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class RedisStockRepository {

    private final RedisTemplate<String, Integer> productStockRedisTemplate;
    private final ValueOperations<String, Integer> productStockValueOperations;

    public RedisStockRepository(final RedisTemplate<String, Integer> productStockRedisTemplate) {
        this.productStockRedisTemplate = productStockRedisTemplate;
        this.productStockValueOperations = productStockRedisTemplate.opsForValue();
    }

    public boolean hasKey(final Long productId) {
        String key = convertLongToString(productId);
        return productStockRedisTemplate.hasKey(key);
    }

    public void setKey(final Long productId, final Stock stock) {
        String key = convertLongToString(productId);
        int value = stock.getStockCount();
        productStockValueOperations.set(key, value);
    }

    public Integer getValue(final Long productId) {
        String key = convertLongToString(productId);
        return productStockValueOperations.get(key);
    }

    // Long을 String으로 변환하는 메서드
    private String convertLongToString(Long value) {
        return String.valueOf(value);
    }
}
