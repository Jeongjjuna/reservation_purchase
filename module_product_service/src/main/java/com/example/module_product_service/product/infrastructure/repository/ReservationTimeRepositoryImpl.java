package com.example.module_product_service.product.infrastructure.repository;

import com.example.module_product_service.product.application.port.ReservationTimeRepository;
import com.example.module_product_service.product.domain.ReservationTime;
import com.example.module_product_service.product.infrastructure.repository.entity.ReservationTimeEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@Repository
public class ReservationTimeRepositoryImpl implements ReservationTimeRepository {

    private final ReservationTimeJpaRepository reservationTimeJpaRepository;

    @Override
    public ReservationTime save(final ReservationTime reservationTime) {
        return reservationTimeJpaRepository.save(ReservationTimeEntity.from(reservationTime)).toModel();
    }
}
