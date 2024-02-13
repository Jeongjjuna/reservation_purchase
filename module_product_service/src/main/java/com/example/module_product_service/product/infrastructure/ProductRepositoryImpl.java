package com.example.module_product_service.product.infrastructure;


import com.example.module_product_service.product.application.port.ProductRepository;
import com.example.module_product_service.product.domain.Product;
import com.example.module_product_service.product.infrastructure.entity.ProductEntity;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

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

    @Override
    public Page<Product> findAll(final Pageable pageable) {
        return productJpaRepository.findAll(pageable).map(ProductEntity::toModel);
    }
}
