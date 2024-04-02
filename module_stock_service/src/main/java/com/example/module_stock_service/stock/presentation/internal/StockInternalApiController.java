package com.example.module_stock_service.stock.presentation.internal;

import com.example.module_stock_service.stock.application.StockService;
import com.example.module_stock_service.stock.domain.Stock;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/v1/internal/stock")
public class StockInternalApiController {

    private final StockService stockService;

    private final RedissonClient redissonClient;


    /**
     * 재고 생성
     */
    @PostMapping("/products/{productId}")
    public ResponseEntity<Stock> createStock(
            @PathVariable("productId") Long productId,
            @RequestBody final Stock productStock
    ) {
        return ResponseEntity.ok(stockService.create(productId, productStock));
    }

    /**
     * 재고 수량 변경
     */
    @PutMapping("/products/{productId}")
    public ResponseEntity<Stock> updateStock(
            @PathVariable("productId") Long productId,
            @RequestBody final Stock productStock
    ) {
        return ResponseEntity.ok(stockService.update(productId, productStock));
    }

    /**
     * 재고 수량 더하기
     */
    @PostMapping( "/increase/products/{productId}")
    public ResponseEntity addStock(
            @PathVariable final Long productId,
            @RequestBody final Stock productStock
    ) {
//        RLock lock = redissonClient.getLock(productId.toString());
//
//        try {
//            boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS);
//
//            if (!available) {
//                log.info("lock 획득 실패");
//                throw new RuntimeException();
//            }
//            stockService.add(productId, productStock);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        } finally {
//            lock.unlock();
//        }
        stockService.addCase1(productId, productStock);
        return ResponseEntity.ok().build();
    }

    /**
     * 재고 수량 빼기
     */
    @PostMapping("/decrease/products/{productId}")
    public ResponseEntity subtractStock(
            @PathVariable final Long productId,
            @RequestBody final Stock productStock
    ) {
//        RLock lock = redissonClient.getLock(productId.toString());
//        try {
//            boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS);
//
//            if(!available) {
//                log.info("lock 획득 실패");
//                throw new RuntimeException();
//            }
//            stockService.subtract(productId, productStock);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        } finally {
//            lock.unlock();
//        }
        stockService.subtractCase1(productId, productStock);
        return ResponseEntity.ok().build();
    }

}

