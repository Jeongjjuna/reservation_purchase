package com.example.module_product_service.product.application;


import com.example.module_product_service.common.exception.GlobalException;
import com.example.module_product_service.product.application.port.ProductRepository;
import com.example.module_product_service.product.application.port.ProductStockRepository;
import com.example.module_product_service.product.domain.Product;
import com.example.module_product_service.product.domain.ProductCreate;
import com.example.module_product_service.product.domain.ProductStock;
import com.example.module_product_service.product.domain.ProductUpdate;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
        final ProductStock productStock = Optional.of(Product.create(productCreate)) // 상품 생성/저장
                .map(productRepository::save)
                .map(saved -> ProductStock.create(saved.getId(), productCreate)) // 상품 재고 생성/저장
                .map(productStockRepository::save)
                .orElseThrow(() -> new GlobalException(HttpStatus.INTERNAL_SERVER_ERROR, "[ERROR] product save fail"));

        return productStock.getProductId();
    }

    /**
     * 상품 정보 수정
     */
    @Transactional
    public void update(final Long productId, final ProductUpdate productUpdate) {
        productRepository.findById(productId)
                .map(product -> product.update(productUpdate))
                .map(productRepository::save)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] product not found"));

        productStockRepository.findById(productId)
                .map(productStock -> productStock.update(productUpdate))
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] product stock not found"));
    }
}
