package com.example.module_stock_service.stock.infrastructure.repository;

import com.example.module_stock_service.stock.infrastructure.repository.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockJpaRepository extends JpaRepository<StockEntity, Long> {
}