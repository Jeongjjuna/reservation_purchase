package com.example.ecommerce_service.product.application;

import com.example.ecommerce_service.product.application.port.ProductRepository;
import com.example.ecommerce_service.product.application.port.ProductStockRepository;
import com.example.ecommerce_service.product.domain.Product;
import com.example.ecommerce_service.product.domain.ProductCreate;
import com.example.ecommerce_service.product.domain.ProductStock;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductStockRepository productStockRepository;

    /**
     * 상품 등록
     */
    @Transactional
    public Long create(final ProductCreate productCreate) {
        final Product product = Product.create(productCreate);

        final Product saved = productRepository.save(product);
        final ProductStock productStock = ProductStock.create(saved.getId(), productCreate);

        return productStockRepository.save(productStock).getProductId();
    }

}
