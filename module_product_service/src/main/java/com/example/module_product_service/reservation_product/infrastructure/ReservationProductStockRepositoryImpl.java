package com.example.module_product_service.reservation_product.infrastructure;


import com.example.module_product_service.reservation_product.application.port.ReservationProductStockRepository;
import com.example.module_product_service.reservation_product.domain.ReservationProductStock;
import com.example.module_product_service.reservation_product.infrastructure.entity.ReservationProductStockEntity;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

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
