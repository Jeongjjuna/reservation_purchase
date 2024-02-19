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

    public Stock readStockCount(final Long productId) {
        return stockRepository.findById(productId)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] reservation product stock not found"));
    }

    @Transactional
    public Stock updateStock(final Long reservationProductId, final Stock reservationProductStock) {
        return stockRepository.findById(reservationProductId)
                .map(productStock -> productStock.update(reservationProductStock.getStockCount()))
                .map(stockRepository::save)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] reservation product stock not found"));
    }
}
