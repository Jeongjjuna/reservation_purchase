package com.example.module_stock_service.stock.application.port;

import com.example.module_stock_service.stock.domain.Stock;
import java.util.Optional;

public interface StockRepository {
    Stock save(Stock stock);

    Optional<Stock> findByProductId(Long productId);

    Optional<Stock> findByProductIdForRead(Long productId);
}
