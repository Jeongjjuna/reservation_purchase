package com.example.module_stock_service.stock.application;

import com.example.module_stock_service.common.exception.GlobalException;
import com.example.module_stock_service.stock.application.port.StockRepository;
import com.example.module_stock_service.stock.domain.Stock;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class StockService {

    private final StockRepository stockRepository;

    /**
     * 재소 수량 조회
     */
    public Stock read(final Long productId) {
        // TODO : 락을 사용하지 않는 읽기 전용 로직으로 변경
        return stockRepository.findByProductIdForRead(productId)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] stock not found"));
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
        // TODO : 임계영역 처리
        stockRepository.findByProductId(productId)
                .map(stock -> stock.addStock(productStock.getStockCount()))
                .map(stockRepository::save)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] product stock not found"));
        // 임계영역 끝
    }

    /**
     * 재고 수량 감소
     */
    @Transactional
    public synchronized void subtract(final Long productId, final Stock productStock) {
        stockRepository.findByProductId(productId)
                .map(stock -> stock.subtractStock(productStock.getStockCount()))
                .map(stockRepository::save)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] product stock not found"));
    }


}
