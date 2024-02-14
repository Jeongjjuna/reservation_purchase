package com.example.module_product_service.reservation_product.infrastructure.repository;


import com.example.module_product_service.reservation_product.application.port.ReservationProductRepository;
import com.example.module_product_service.reservation_product.domain.ReservationProduct;
import com.example.module_product_service.reservation_product.infrastructure.repository.entity.ReservationProductEntity;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@AllArgsConstructor
@Repository
public class ReservationProductRepositoryImpl implements ReservationProductRepository {

    private final ReservationProductJpaRepository reservationProductJpaRepository;

    @Override
    public ReservationProduct save(final ReservationProduct reservationProduct) {
        return reservationProductJpaRepository.save(ReservationProductEntity.from(reservationProduct)).toModel();
    }

    @Override
    public Optional<ReservationProduct> findById(final Long productId) {
        return reservationProductJpaRepository.findById(productId).map(ReservationProductEntity::toModel);
    }

    @Override
    public Page<ReservationProduct> findAll(final Pageable pageable) {
        return reservationProductJpaRepository.findAll(pageable).map(ReservationProductEntity::toModel);
    }
}
