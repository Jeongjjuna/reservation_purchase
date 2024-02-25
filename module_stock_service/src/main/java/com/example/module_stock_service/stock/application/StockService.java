package com.example.module_stock_service.stock.application;

import com.example.module_stock_service.common.exception.GlobalException;
import com.example.module_stock_service.stock.application.port.StockRepository;
import com.example.module_stock_service.stock.domain.Stock;
import com.example.module_stock_service.stock.infrastructure.repository.RedisStockRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@AllArgsConstructor
@Service
public class StockService {

    private final StockRepository stockRepository;

    private final RedisStockRepository redisStockRepository;

    /**
     * 재소 수량 조회
     */
    public Stock read(final Long productId) {

        // redis에 값이 존재한다면 조회 후 리턴
        if (redisStockRepository.hasKey(productId)) {
            int stockCount = redisStockRepository.getValue(productId);
            return Stock.builder()
                    .productId(productId)
                    .stockCount(stockCount)
                    .build();
        }

        Stock preStock = stockRepository.findByProductIdForRead(productId)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] 해당 상품의 재고 정보를 찾을 수 없습니다."));
        redisStockRepository.setKey(productId, preStock);
        return preStock;
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
     * redis의 원자적 연산을 위해서 동기화 기법 사용. -> 여러대의 서버를 운영할 경우 별도의 redis원자적 연산을 고려해봐야함(ex Lua)
     */
    @Transactional
    public void add(final Long productId, final Stock productStock) {
        synchronized (this) { // TODO : DB에 언제 갱신해줄 것인가?
            Long incrementResult = redisStockRepository.increase(productId, productStock.getStockCount());
            log.info("재고 증가 후 : " + incrementResult);
        }
    }

    /**
     * 재고 수량 감소
     * redis의 원자적 연산을 위해서 동기화 기법 사용. -> 여러대의 서버를 운영할 경우 별도의 redis원자적 연산을 고려해봐야함(ex Lua)
     */
    @Transactional
    public void subtract(final Long productId, final Stock productStock) {
        synchronized (this) {
            if (redisStockRepository.decrease(productId, productStock.getStockCount()) < 0) {
                Long restoreResult = redisStockRepository.increase(productId, productStock.getStockCount());
                log.info("복구 후 : " + restoreResult);
                throw new GlobalException(HttpStatus.CONFLICT, "[ERROR] 재고 수량이 부족 합니다.");
            }
        }
    }

}
