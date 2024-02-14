package com.example.module_product_service.product.infrastructure.repository;


import com.example.module_product_service.product.application.port.ProductStockRepository;
import com.example.module_product_service.product.domain.ProductStock;
import com.example.module_product_service.product.infrastructure.repository.entity.ProductStockEntity;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

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
