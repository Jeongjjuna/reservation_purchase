package com.example.module_stock_service.stock.infrastructure.repository;

import com.example.module_stock_service.stock.application.port.StockRepository;
import com.example.module_stock_service.stock.domain.Stock;
import com.example.module_stock_service.stock.infrastructure.repository.entity.StockEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class StockRepositoryImpl implements StockRepository {

    private final StockJpaRepository productStockJpaRepository;

    @Override
    public Stock save(final Stock productStock) {
        return productStockJpaRepository.save(StockEntity.from(productStock)).toModel();
    }

    @Override
    public Optional<Stock> findById(final Long productId) {
        return productStockJpaRepository.findById(productId).map(StockEntity::toModel);
    }
}