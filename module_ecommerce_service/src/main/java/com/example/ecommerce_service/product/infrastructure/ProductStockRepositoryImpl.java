package com.example.ecommerce_service.product.infrastructure;

import com.example.ecommerce_service.product.application.port.ProductStockRepository;
import com.example.ecommerce_service.product.domain.ProductStock;
import com.example.ecommerce_service.product.infrastructure.entity.ProductStockEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class ProductStockRepositoryImpl implements ProductStockRepository {

    private final ProductStockJpaRepository productStockJpaRepository;

    @Override
    public ProductStock save(final ProductStock productStock) {
        return productStockJpaRepository.save(ProductStockEntity.from(productStock)).toModel();
    }

    @Override
    public Optional<ProductStock> findById(final Long productId) {
        return productStockJpaRepository.findById(productId).map(ProductStockEntity::toModel);
    }
}
