package com.example.module_stock_service.stock.application;

import com.example.module_stock_service.common.exception.GlobalException;
import com.example.module_stock_service.stock.application.port.StockRepository;
import com.example.module_stock_service.stock.domain.Stock;
import com.example.module_stock_service.stock.infrastructure.repository.RedisStockRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Service
public class StockService {

    private final RedissonClient redissonClient;

    private final StockRepository stockRepository;

    private final RedisStockRepository redisStockRepository;

    /**
     * 재소 수량 조회
     */
    public Stock read(final Long productId) {

        try {
            /**
             * redis 에 재고 수량이 존재 하는 경우 값을 반환
             */
            if (redisStockRepository.hasKey(productId)) {
                int stockCount = redisStockRepository.getValue(productId);
                return Stock.builder()
                        .productId(productId)
                        .stockCount(stockCount)
                        .build();
            }

            /**
             * redis 에 재고 수량이 존재 하지 않는 다면 데이터베이스에서 redis 로 값을 로드
             */
            Stock preStock = stockRepository.findByProductIdForRead(productId)
                    .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] 해당 상품의 재고 정보를 찾을 수 없습니다."));
            redisStockRepository.setKey(productId, preStock);
            return preStock;
        } catch (Exception e){
            throw new IllegalArgumentException("redis 장애 발생");
        }
    }

    /**
     * 재고 생성(상품 최초 등록 시)
     */
    @Transactional
    public Stock create(final Long productId, final Stock productStock) {
        return stockRepository.save(productStock);
    }

    /**
     * 재소 수량 변경
     */
    @Transactional
    public Stock update(final Long reservationProductId, final Stock reservationProductStock) {
        // TODO : 임계영역 처리
        return stockRepository.findByProductId(reservationProductId)
                .map(productStock -> productStock.update(reservationProductStock.getStockCount()))
                .map(stockRepository::save)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] 해당 상품의 재고 정보를 찾을 수 없습니다."));
    }

    /**
     * 재소 수량 증가
     */
    @Transactional
    public void add(final Long productId, final Stock productStock) {
        synchronized (this) {
            try {
                Long incrementResult = redisStockRepository.increase(productId, productStock.getStockCount());
                log.info("재고 증가 후 : " + incrementResult);
            } catch (Exception e) {
                throw new IllegalArgumentException("redis 장애 발생");
            }
        }
    }

    /**
     * 재고 수량 감소
     */
    @Transactional
    public void subtract(final Long productId, final Stock productStock) {
        synchronized (this) {
            try {
                if (redisStockRepository.decrease(productId, productStock.getStockCount()) < 0) {
                    Long restoreResult = redisStockRepository.increase(productId, productStock.getStockCount());
                    log.info("복구 후 : " + restoreResult);
                    throw new GlobalException(HttpStatus.CONFLICT, "[ERROR] 재고 수량이 부족 합니다.");
                }
            } catch (GlobalException e) {
                throw e;
            } catch (Exception e) {
                throw new IllegalArgumentException("redis 장애 발생");
            }
        }
    }

}
