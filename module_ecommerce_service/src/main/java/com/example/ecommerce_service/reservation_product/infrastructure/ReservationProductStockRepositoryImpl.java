package com.example.ecommerce_service.reservation_product.infrastructure;

import com.example.ecommerce_service.reservation_product.application.port.ReservationProductStockRepository;
import com.example.ecommerce_service.reservation_product.domain.ReservationProductStock;
import com.example.ecommerce_service.reservation_product.infrastructure.entity.ReservationProductStockEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class ReservationProductStockRepositoryImpl implements ReservationProductStockRepository {

    private final ReservationProductStockJpaRepository reservationProductStockJpaRepository;

    @Override
    public ReservationProductStock save(final ReservationProductStock reservationProductStock) {
        return reservationProductStockJpaRepository.save(ReservationProductStockEntity.from(reservationProductStock)).toModel();
    }

    @Override
    public Optional<ReservationProductStock> findById(final Long productId) {
        return reservationProductStockJpaRepository.findById(productId).map(ReservationProductStockEntity::toModel);
    }
}
