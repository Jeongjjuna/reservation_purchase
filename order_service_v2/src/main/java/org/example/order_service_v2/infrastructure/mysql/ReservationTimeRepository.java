package org.example.order_service_v2.infrastructure.mysql;

import org.example.order_service_v2.infrastructure.mysql.entity.ReservationTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ReservationTimeRepository extends JpaRepository<ReservationTimeEntity, Long> {
    Optional<ReservationTimeEntity> findByProductId(Long productId);
}
