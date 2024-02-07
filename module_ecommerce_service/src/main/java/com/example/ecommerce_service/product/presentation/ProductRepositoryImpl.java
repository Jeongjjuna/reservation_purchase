package com.example.ecommerce_service.product.presentation;

import com.example.ecommerce_service.product.application.port.ProductRepository;
import com.example.ecommerce_service.product.domain.Product;
import com.example.ecommerce_service.product.infrastructure.entity.ProductEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    @Override
    public Product save(final Product product) {
        return productJpaRepository.save(ProductEntity.from(product)).toModel();
    }

    @Override
    public Optional<Product> findById(final Long productId) {
        return productJpaRepository.findById(productId).map(ProductEntity::toModel);
    }
}
