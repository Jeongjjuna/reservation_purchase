package com.example.module_product_service.product.infrastructure.repository;

import com.example.module_product_service.product.application.port.ProductStockRepository;
import com.example.module_product_service.product.domain.ProductStock;
import com.example.module_product_service.product.infrastructure.repository.entity.ProductStockEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class ProductStockRepositoryImpl implements ProductStockRepository {

    private final ReservationProductStockJpaRepository reservationProductStockJpaRepository;

    @Override
    public ProductStock save(final ProductStock productStock) {
        return reservationProductStockJpaRepository.save(ProductStockEntity.from(productStock)).toModel();
    }

    @Override
    public Optional<ProductStock> findById(final Long productId) {
        return reservationProductStockJpaRepository.findById(productId).map(ProductStockEntity::toModel);
    }
}
