package com.example.module_stock_service.stock.application;

import com.example.module_stock_service.common.exception.GlobalException;
import com.example.module_stock_service.stock.application.port.StockRepository;
import com.example.module_stock_service.stock.domain.Stock;
import com.example.module_stock_service.stock.infrastructure.repository.RedisStockRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        // 그렇지 않으면 DB에서 조회 후 redis에 저장
        // TODO : 락을 사용하지 않는 읽기 전용 로직으로 변경
        Stock preStock = stockRepository.findByProductIdForRead(productId)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] stock not found"));
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
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] reservation product stock not found"));
    }

    /**
     * 재소 수량 증가
     */
    @Transactional
    public synchronized void add(final Long productId, final Stock productStock) {
        // DB에 반영한다.
        Stock preStock = stockRepository.findByProductId(productId)
                .map(stock -> stock.addStock(productStock.getStockCount()))
                .map(stockRepository::save)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] product stock not found"));

        // DB에 최신화된 재고 수량정보를 redis에 넣어준다.
        redisStockRepository.setKey(productId, preStock);
    }

    /**
     * 재고 수량 감소
     */
    @Transactional
    public synchronized void subtract(final Long productId, final Stock productStock) {
        Stock preStock = stockRepository.findByProductId(productId)
                .map(stock -> stock.subtractStock(productStock.getStockCount()))
                .map(stockRepository::save)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] product stock not found"));

        // DB에 최신화된 재고 수량정보를 redis에 넣어준다.
        redisStockRepository.setKey(productId, preStock);
    }


}
