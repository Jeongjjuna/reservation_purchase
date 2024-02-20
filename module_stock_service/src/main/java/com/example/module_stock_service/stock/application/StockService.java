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
    public Stock readStockCount(final Long productId) {
        return stockRepository.findById(productId)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] reservation product stock not found"));
    }

    /**
     * 재고 생성(상품 최초 등록 시)
     */
    public Stock create(final Long productId, final Stock productStock) {
        return stockRepository.save(productStock);
    }

    /**
     * 재소 수량 변경
     */
    @Transactional
    public Stock update(final Long reservationProductId, final Stock reservationProductStock) {
        // TODO : 임계영역 처리
        return stockRepository.findById(reservationProductId)
                .map(productStock -> productStock.update(reservationProductStock.getStockCount()))
                .map(stockRepository::save)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] reservation product stock not found"));
    }

    /**
     * 재소 수량 증가
     */
    @Transactional
    public void add(final Long productId, final Stock productStock) {
        // TODO : 임계영역 처리
        stockRepository.findById(productId)
                .map(stock -> stock.addStock(productStock.getStockCount()))
                .map(stockRepository::save)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] product stock not found"));
        // 임계영역 끝
    }

    /**
     * 재고 수량 감소
     */
    @Transactional
    public void subtract(final Long productId, final Stock productStock) {
        // TODO : 임계영역 처리
        stockRepository.findById(productId)
                .map(stock -> stock.subtractStock(productStock.getStockCount()))
                .map(stockRepository::save)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] product stock not found"));
        // 임계영역 끝
    }

}
