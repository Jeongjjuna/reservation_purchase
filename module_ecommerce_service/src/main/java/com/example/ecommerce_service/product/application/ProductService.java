package com.example.ecommerce_service.product.application;

import com.example.ecommerce_service.common.exception.GlobalException;
import com.example.ecommerce_service.product.application.port.ProductRepository;
import com.example.ecommerce_service.product.domain.Product;
import com.example.ecommerce_service.product.domain.ProductCreate;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Long create(final ProductCreate productCreate) {
        final Product product = Product.create(productCreate);
        return productRepository.save(product).getId();
    }

    public Product find(final Long productId) {
        System.out.println("test");
        return productRepository.findById(productId).orElseThrow(() ->
                new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] product not found"));
    }
}
