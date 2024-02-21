package com.example.module_stock_service.stock.infrastructure.repository;

import com.example.module_stock_service.stock.infrastructure.repository.entity.StockEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import java.util.Optional;

public interface StockJpaRepository extends JpaRepository<StockEntity, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE) // 주의사항 데드락
    Optional<StockEntity> findById(Long productId);
}