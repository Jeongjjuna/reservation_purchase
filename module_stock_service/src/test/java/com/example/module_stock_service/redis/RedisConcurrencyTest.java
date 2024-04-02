package com.example.module_stock_service.redis;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.module_stock_service.stock.application.StockService;
import com.example.module_stock_service.stock.domain.Stock;
import com.example.module_stock_service.stock.infrastructure.repository.RedisStockRepository;
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
public class RedisConcurrencyTest {

    @Autowired
    private StockService stockService;

    @Autowired
    private RedisStockRepository redisStockRepository;

    /**
     * redis에 최초 1회 재고수량정보를 등록한다.
     */
    @BeforeEach
    public void insert() throws InterruptedException {
        Stock stock = new Stock(1L, 10000);
        redisStockRepository.setKey(1L, stock);
    }

    /**
     * 테스트가 끝나면 redis정보를 삭제한다.
     */
    @AfterEach
    public void delete() {
        redisStockRepository.delete(1L);
    }

    @DisplayName("GET, SET 동시성문제 발생 테스트 : 재고 10000개에 대하여 동시에 5000개 감소, 5000개 증가 요청시 남은 수량이 10000개가 아니다.")
    @Test
    public void CaseFail_동시성_문제_발생_테스트() throws InterruptedException {
        // given
        int threadCount = 10000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {

            if (i % 2 == 0) {
                executorService.submit(() -> {
                    try {
                        stockService.subtractCaseFail(1L, new Stock(1L, 1));
                    } finally {
                        latch.countDown();
                    }
                });
            } else {
                executorService.submit(() -> {
                    try {
                        stockService.addCaseFail(1L, new Stock(1L, 1));
                    } finally {
                        latch.countDown();
                    }
                });
            }
        }

        latch.await();

        Integer stockCount = redisStockRepository.getValue(1L);

        // then
        assertThat(stockCount).isNotEqualTo(10000);
    }

    @DisplayName("INCR, DECR를 통한 재고 동시성 테스트 : 재고 10000개에 대하여 동시에 5000개 감소, 5000개 증가 요청시 남은 수량이 10000개이다.")
    @Test
    public void  Case0_원자적연산_동시성_문제_해결_테스트_1() throws InterruptedException {
        int threadCount = 10000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {

            if (i % 2 == 0) {
                executorService.submit(() -> {
                    try {
                        stockService.subtractCase0(1L, new Stock(1L, 1));
                    } finally {
                        latch.countDown();
                    }
                });
            } else {
                executorService.submit(() -> {
                    try {
                        stockService.addCase0(        // given
                                1L, new Stock(1L, 1));
                    } finally {
                        latch.countDown();
                    }
                });
            }
        }

        latch.await();

        Integer stockCount = redisStockRepository.getValue(1L);

        // then
        assertThat(stockCount).isEqualTo(10000);
    }

    @DisplayName("INCR, DECR를 통한 재고 동시성 테스트 : 재고 10000개에 대하여 동시에 20000개 감소 시 남은수량이 0개이다.")
    @Test
    public void Case0_원자적연산_동시성_문제_해결_테스트_2() throws InterruptedException {
        // given
        int threadCount = 20000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {
                executorService.submit(() -> {
                    try {
                        stockService.subtractCase0(1L, new Stock(1L, 1));
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

    @DisplayName("분산락을 통한 재고 동시성 테스트 : 재고 10000개에 대하여 동시에 5000개 감소, 5000개 증가 요청시 남은 수량이 10000개이다.")
    @Test
    public void  Case1_분산락을_이용한_동시성_문제_해결_테스트_1() throws InterruptedException {
        int threadCount = 10000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {

            if (i % 2 == 0) {
                executorService.submit(() -> {
                    try {
                        stockService.subtractCase1(1L, new Stock(1L, 1));
                    } finally {
                        latch.countDown();
                    }
                });
            } else {
                executorService.submit(() -> {
                    try {
                        stockService.addCase1(
                                1L, new Stock(1L, 1));
                    } finally {
                        latch.countDown();
                    }
                });
            }
        }

        latch.await();

        Integer stockCount = redisStockRepository.getValue(1L);

        // then
        assertThat(stockCount).isEqualTo(10000);
    }

    @DisplayName("분산락을 통한 재고 동시성 테스트 : 재고 10000개에 대하여 동시에 20000개 감소 시 남은수량이 0개이다.")
    @Test
    public void Case1_분산락_이용한_동시성_문제_해결_테스트_2() throws InterruptedException {
        // given
        int threadCount = 20000;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // when
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    stockService.subtractCase1(1L, new Stock(1L, 1));
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
