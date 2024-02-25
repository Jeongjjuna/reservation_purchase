package com.example.module_stock_service.stock.infrastructure.repository;

import com.example.module_stock_service.stock.domain.Stock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class RedisStockRepository {

    private static final String PREFIX = "product id : ";

    private final RedisTemplate<String, Integer> productStockRedisTemplate;

    public RedisStockRepository(final RedisTemplate<String, Integer> productStockRedisTemplate) {
        this.productStockRedisTemplate = productStockRedisTemplate;
    }

    public boolean hasKey(final Long productId) {
        String key = convertLongToString(productId);
        return productStockRedisTemplate.hasKey(PREFIX + key);
    }

    public void setKey(final Long productId, final Stock stock) {
        String key = PREFIX + convertLongToString(productId);
        int value = stock.getStockCount();
        productStockRedisTemplate
                .opsForValue()
                .set(key, value);
    }

    public Integer getValue(final Long productId) {
        String key = PREFIX + convertLongToString(productId);
        return productStockRedisTemplate
                .opsForValue()
                .get(key);
    }

    public Long increase(final Long productId, final int quantity) {
        String key = PREFIX + convertLongToString(productId);
        return productStockRedisTemplate
                .opsForValue()
                .increment(key, quantity);
    }

    public Long decrease(final Long productId, final int quantity) {
        String key = PREFIX + convertLongToString(productId);
        return productStockRedisTemplate
                .opsForValue()
                .decrement(key, quantity);
    }

    public Boolean delete(final Long productId) {
        String key = PREFIX + convertLongToString(productId);
        return productStockRedisTemplate.delete(key);
    }

    private String convertLongToString(Long value) {
        return String.valueOf(value);
    }
}
