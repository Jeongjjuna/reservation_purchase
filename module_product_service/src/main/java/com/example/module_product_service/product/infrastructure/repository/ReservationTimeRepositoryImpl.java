package com.example.module_product_service.product.infrastructure.repository;

import com.example.module_product_service.product.application.port.ReservationTimeRepository;
import com.example.module_product_service.product.domain.ReservationTime;
import com.example.module_product_service.product.infrastructure.repository.entity.ReservationTimeEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class ReservationTimeRepositoryImpl implements ReservationTimeRepository {

    private final ReservationTimeJpaRepository reservationTimeJpaRepository;

    @Override
    public ReservationTime save(final ReservationTime reservationTime) {
        return reservationTimeJpaRepository.save(ReservationTimeEntity.from(reservationTime)).toModel();
    }

    @Override
    public Optional<ReservationTime> findByProductId(final Long productId) {
        return reservationTimeJpaRepository.findById(productId).map(ReservationTimeEntity::toModel);
    }
}
