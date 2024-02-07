package com.example.ecommerce_service.product.application.port;

import com.example.ecommerce_service.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface ProductRepository {
    Product save(Product product);

    Optional<Product> findById(Long productId);

    Page<Product> findAll(Pageable pageable);
}
