package com.example.module_stock_service.redis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.module_stock_service.stock.application.StockService;
import com.example.module_stock_service.stock.domain.Stock;
import com.example.module_stock_service.stock.infrastructure.repository.StockJpaRepository;
import com.example.module_stock_service.stock.infrastructure.repository.entity.StockEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class StockTest {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockJpaRepository stockJpaRepository;

    @BeforeEach
    public void insert() {
        Stock stock = new Stock(1L, 100);
        stockJpaRepository.saveAndFlush(StockEntity.from(stock));
    }

    @AfterEach
    public void delete() {
        stockJpaRepository.deleteAll();
    }

    @Test
    public void 동시에_100개의요청() throws InterruptedException {
        // given
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    stockService.subtract(1L, new Stock(1L, 1));
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        StockEntity stock = stockJpaRepository.findById(1L).orElseThrow();

        // then
        assertEquals(0, stock.getStockCount());
    }
}
