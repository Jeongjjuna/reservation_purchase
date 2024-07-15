package org.example.order_service_v2.infrastructure.mysql;

import org.example.order_service_v2.infrastructure.mysql.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StockRepository extends JpaRepository<StockEntity, Long> {
    Optional<StockEntity> findByProductId(Long productId);
}