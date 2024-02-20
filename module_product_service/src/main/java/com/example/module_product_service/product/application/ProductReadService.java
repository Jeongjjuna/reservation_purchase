package com.example.module_product_service.product.application;

import com.example.module_product_service.common.exception.GlobalException;
import com.example.module_product_service.product.application.port.ProductRepository;
import com.example.module_product_service.product.domain.Product;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ProductReadService {

    private final ProductRepository productRepository;

    public Product find(final Long productId) {
        return productRepository.findById(productId).orElseThrow(() ->
                new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] reservation product not found"));
    }

    public Page<Product> findAll() {
        final Pageable pageable = PageRequest.of(0, 20);
        return productRepository.findAll(pageable);
    }
}
