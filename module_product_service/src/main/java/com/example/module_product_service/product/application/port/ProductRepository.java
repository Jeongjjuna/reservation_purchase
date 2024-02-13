package com.example.module_product_service.product.application.port;


import com.example.module_product_service.product.domain.Product;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepository {
    Product save(Product product);

    Optional<Product> findById(Long productId);

    Page<Product> findAll(Pageable pageable);
}
