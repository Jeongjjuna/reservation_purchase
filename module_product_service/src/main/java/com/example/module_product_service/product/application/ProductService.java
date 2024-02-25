package com.example.module_product_service.product.application;

import com.example.module_product_service.common.exception.GlobalException;
import com.example.module_product_service.product.application.port.ProductRepository;
import com.example.module_product_service.product.application.port.ReservationTimeRepository;
import com.example.module_product_service.product.application.port.StockServiceAdapter;
import com.example.module_product_service.product.domain.Product;
import com.example.module_product_service.product.domain.ProductCreate;
import com.example.module_product_service.product.domain.ProductStock;
import com.example.module_product_service.product.domain.ProductUpdate;
import com.example.module_product_service.product.domain.ReservationTime;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ReservationTimeRepository reservationTimeRepository;
    private final StockServiceAdapter stockServiceAdapter;

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
                .orElseThrow(() -> new GlobalException(HttpStatus.INTERNAL_SERVER_ERROR, "[ERROR] 상품 저장에 실패 했습니다."));
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
                .orElseThrow(() -> new GlobalException(HttpStatus.INTERNAL_SERVER_ERROR, "[ERROR] 상품 수정에 실패 했습니다."));

        requestUpdateStockCount(productId, productUpdate);
    }

    /**
     * feign client 요청
     */
    private void requestUpdateStockCount(final Long productId, final ProductUpdate productUpdate) {
        ProductStock productStock = ProductStock.builder()
                .productId(productId)
                .stockCount(productUpdate.getStockCount())
                .build();
        stockServiceAdapter.updateStockCount(productId, productStock);
    }

    private Product saveReservationTime(Product product, ProductCreate productCreate) {
        ReservationTime reservationTime = ReservationTime.create(product, productCreate);
        reservationTimeRepository.save(reservationTime);
        return product;
    }

    private Product saveProductStock(Product product, ProductCreate productCreate) {
        // 재고 생성 요청(재고서비스에 feign 요청)
        ProductStock productStock = ProductStock.builder()
                .productId(product.getId())
                .stockCount(productCreate.getStockCount())
                .build();
        stockServiceAdapter.createStockCount(product.getId(), productStock);
        return product;
    }
}
