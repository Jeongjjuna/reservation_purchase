package com.example.module_stock_service.stock.infrastructure.repository;

import com.example.module_stock_service.stock.infrastructure.repository.entity.StockEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface StockJpaRepository extends JpaRepository<StockEntity, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE) // 주의사항 데드락
    Optional<StockEntity> findByProductId(Long productId);

    @Query("SELECT s FROM StockEntity s WHERE s.productId = :productId")
    Optional<StockEntity> findByProductIdForRead(Long productId);
}