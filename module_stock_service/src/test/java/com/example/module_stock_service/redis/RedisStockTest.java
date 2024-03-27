package com.example.module_stock_service.redis;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.module_stock_service.common.exception.GlobalException;
import com.example.module_stock_service.stock.domain.Stock;
import com.example.module_stock_service.stock.infrastructure.repository.RedisStockRepository;
import com.example.module_stock_service.stock.infrastructure.repository.StockJpaRepository;
import com.example.module_stock_service.stock.presentation.internal.StockInternalApiController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
public class RedisStockTest {

    @Autowired
    private StockInternalApiController stockInternalApiController;

    @Autowired
    private RedisStockRepository redisStockRepository;

    @Autowired
    private StockJpaRepository stockJpaRepository;

    /**
     * redis에 최초 1회 재고수량정보를 등록한다.
     */
    @BeforeEach
    public void insert() throws InterruptedException {
        Stock stock = new Stock(1L, 100);
        redisStockRepository.setKey(1L, stock);
    }

    /**
     * 테스트가 끝나면 redis정보를 삭제한다.
     */
    @AfterEach
    public void delete() {
        redisStockRepository.delete(1L);
    }

    @DisplayName("재고가 0개일 때 재고감소시 예외반환")
    @Test
    public void 재고가_0개일때_재고감소시_예외반환() throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(100);

        // when, then
        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                try {
                    stockInternalApiController.subtractStock(1L, new Stock(1L, 1));
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();

        assertThatThrownBy(() -> stockInternalApiController.subtractStock(1L, new Stock(1L, 1)))
                .isInstanceOf(GlobalException.class);
    }

    @DisplayName("재고 100개에 대하여 동시에 80개 감소, 20개 증가 요청시 남은 수량이 40개인가")
    @Test
    public void 재고가_100개일때_80감소_20증가요청() throws InterruptedException {

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

        Integer stockCount = redisStockRepository.getValue(1L);

        // then
        assertThat(stockCount).isEqualTo(40);
    }

    @DisplayName("redis의 재고 100개에 대하여 동시에 80개 요청시 남은 수량이 20개인가")
    @Test
    public void 재고가_100개일때_동시에_80개의요청() throws InterruptedException {
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

        Integer stockCount = redisStockRepository.getValue(1L);

        // then
        assertThat(stockCount).isEqualTo(20);
    }

    @DisplayName("redis의 재고 100개에 대하여 동시에 100개 요청시 남은 수량이 0개인가")
    @Test
    public void 재고가_100개일때_동시에_100개의요청() throws InterruptedException {
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

        Integer stockCount = redisStockRepository.getValue(1L);

        // then
        assertThat(stockCount).isEqualTo(0);
    }
}
