package com.example.module_stock_service.mock;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixtureStockTest {

    @DisplayName("subtractCase1() 동시성 테스트 : 재고수량 300개일때 1000000감소 요청 시 재고수량 0개 남음")
    @Test
    void synchronized_1000000개감소_test() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(1000000);

        FixtureStock stock = new FixtureStock(1L, 300);

        for (int i = 0; i < 1000000; i++) {
            executorService.submit(() -> {
                try {
                    stock.subtractCase1(1);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        assertThat(stock.getStockCount()).isEqualTo(0);
    }

    @DisplayName("subtractCase1() 동시성 테스트 : 재고수량 100개일때 100감소 요청 시 재고수량 0개 남음")
    @Test
    void synchronized_100개감소_test() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(16);
        CountDownLatch countDownLatch = new CountDownLatch(100);

        FixtureStock stock = new FixtureStock(1L, 100);

        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                try {
                    stock.subtractCase1(1);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        countDownLatch.await();

        assertThat(stock.getStockCount()).isEqualTo(0);
    }

    @DisplayName("subtractCase2() 동시성 테스트 : 재고수량 1개중 2개의 요청시, -1개의 재고수량 동시성 문제 발생")
    @Test
    void synchronized_test_subtractCase2() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CountDownLatch countDownLatch = new CountDownLatch(2);

        FixtureStock stock = new FixtureStock(1L, 1);

        // thread 1
        executorService.submit(() -> {
            try {
                stock.subtractCase2(1, 99);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                countDownLatch.countDown();
            }
        });

        // thread 2
        executorService.submit(() -> {
            try {
                stock.subtractCase2(1, 100);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                countDownLatch.countDown();
            }
        });

        countDownLatch.await();

        assertThat(stock.getStockCount()).isEqualTo(-1);
    }
}
