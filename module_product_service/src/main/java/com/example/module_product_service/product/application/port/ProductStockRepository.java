package com.example.module_product_service.product.application.port;

import com.example.module_product_service.product.domain.ProductStock;
import java.util.Optional;

public interface ProductStockRepository {
    ProductStock save(ProductStock productStock);

    Optional<ProductStock> findById(Long productId);
}
