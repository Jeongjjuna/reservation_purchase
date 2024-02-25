package com.example.module_stock_service.stock.presentation;

import com.example.module_stock_service.stock.domain.Stock;
import com.example.module_stock_service.stock.infrastructure.repository.RedisStockRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisStockRepository redisStockRepository;

    @DisplayName("Redis 동작 테스트")
    @Test
    void redis() {
        System.out.println(redisStockRepository.delete(1L));

        Stock stock = new Stock(1L, 3);
        redisStockRepository.setKey(1L, stock);

        System.out.println(redisStockRepository.decrease(1L, 2));

        System.out.println("---");
        System.out.println(redisStockRepository.decrease(1L, 2));
        System.out.println(redisStockRepository.decrease(1L, 2));
        System.out.println("---");

        System.out.println(redisStockRepository.getValue(1L));
    }

    @DisplayName("Key값이 없을 때 감소 테스트")
    @Test
    void redis_감소() {
        System.out.println(redisStockRepository.delete(1L));

        System.out.println(redisStockRepository.decrease(1L, 4));

        System.out.println(redisStockRepository.getValue(1L));
        // Key가 없을 때 0부터 시작한다.
    }
}
