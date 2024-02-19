package com.example.module_product_service.product.application;

import com.example.module_product_service.common.exception.GlobalException;
import com.example.module_product_service.product.application.port.ProductRepository;
import com.example.module_product_service.product.application.port.ProductStockRepository;
import com.example.module_product_service.product.application.port.ReservationTimeRepository;
import com.example.module_product_service.product.domain.Product;
import com.example.module_product_service.product.domain.ProductCreate;
import com.example.module_product_service.product.domain.ProductStock;
import com.example.module_product_service.product.domain.ProductUpdate;
import com.example.module_product_service.product.domain.ReservationTime;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ReservationTimeRepository reservationTimeRepository;
    private final ProductStockRepository productStockRepository;

    /**
     * 상품 등록
     */
    @Transactional
    public Long create(final ProductCreate productCreate) {
        // TODO: 클래스/기능 단위로 분리하고, 코드 스타일을 통일해보자. 리팩토링 가능
        final Product saved = Optional.of(Product.create(productCreate)) // 상품 생성
                .map(product -> productRepository.save(product))  // 상품 저장
                .map(product -> saveReservationTime(product, productCreate)) // 상품 예약 시간 저장
                .map(product -> saveProductStock(product, productCreate)) // 상품 재고 수량 저장
                .orElseThrow(() -> new GlobalException(HttpStatus.INTERNAL_SERVER_ERROR, "[ERROR] reservation product save fail"));
        return saved.getId();
    }

    /**
     * 상품 정보 수정
     */
    @Transactional
    public void update(final Long productId, final ProductUpdate productUpdate) {
        productRepository.findById(productId)
                .map(product -> product.update(productUpdate))
                .map(productRepository::save)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] reservation product not found"));

        productStockRepository.findById(productId)
                .map(productStock -> productStock.update(productUpdate.getStockCount()))
                .map(productStockRepository::save)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] reservation product stock not found"));
    }

    /**
     * 재소 수량 변경
     */
    @Transactional
    public ProductStock updateStock(final Long reservationProductId, final ProductStock reservationProductStock) {
        return productStockRepository.findById(reservationProductId)
                .map(productStock -> productStock.update(reservationProductStock.getStockCount()))
                .map(productStockRepository::save)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] reservation product stock not found"));
    }

    private Product saveReservationTime(Product product, ProductCreate productCreate) {
        ReservationTime reservationTime = ReservationTime.create(product, productCreate);
        reservationTimeRepository.save(reservationTime);
        return product;
    }

    private Product saveProductStock(Product product, ProductCreate productCreate) {
        ProductStock productStock = ProductStock.create(product.getId(), productCreate);
        productStockRepository.save(productStock);
        return product;
    }
}
