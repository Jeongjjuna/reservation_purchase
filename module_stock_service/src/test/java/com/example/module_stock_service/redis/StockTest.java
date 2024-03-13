package com.example.module_stock_service.redis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.module_stock_service.stock.application.StockService;
import com.example.module_stock_service.stock.domain.Stock;
import com.example.module_stock_service.stock.infrastructure.repository.StockJpaRepository;
import com.example.module_stock_service.stock.infrastructure.repository.entity.StockEntity;
import com.example.module_stock_service.stock.presentation.internal.StockInternalApiController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
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
    private StockInternalApiController stockInternalApiController;

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

    @DisplayName("재고 100개에 대하여 동시에 80개 감소, 20개 증가 요청시 남은 수량이 40개인가")
    @Test
    public void 재고가_100개일떄_80감소_20증가요청() throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(100);

        // when
        for (int i = 0; i < 80; i++) {
            executorService.submit(() -> {
                try {
                    stockInternalApiController.subtractStock(1L, new Stock(1L, 1));
                } finally {
                    latch.countDown();
                }
            });
        }

        for (int i = 0; i < 20; i++) {
            executorService.submit(() -> {
                try {
                    stockInternalApiController.addStock(1L, new Stock(1L, 1));
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        StockEntity stock = stockJpaRepository.findById(1L).orElseThrow();

        // then
        assertEquals(40,  stock.getStockCount());
    }


    @DisplayName("재고 100개에 대하여 동시에 80개 요청시 남은 수량이 20개인가")
    @Test
    public void 재고가_100개일떄_동시에_80개의요청() throws InterruptedException {
        // given
        int threadCount = 80;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    stockInternalApiController.subtractStock(1L, new Stock(1L, 1));
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        StockEntity stock = stockJpaRepository.findById(1L).orElseThrow();

        // then
        assertEquals(20,  stock.getStockCount());
    }

    @DisplayName("재고 100개에 대하여 동시에 120개 요청시 남은 수량이 0개인가")
    @Test
    public void 재고가_100개일떄_동시에_120개의요청() throws InterruptedException {
        // given
        int threadCount = 120;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    stockInternalApiController.subtractStock(1L, new Stock(1L, 1));
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

    @DisplayName("재고 100개에 대하여 동시에 100개 요청시 남은 수량이 0개인가")
    @Test
    public void 재고가_100개일떄_동시에_100개의요청() throws InterruptedException {
        // given
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    stockInternalApiController.subtractStock(1L, new Stock(1L, 1));
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

    @Disabled("redis 락을 컨트롤러에서 컨트롤 하도록 변경")
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
