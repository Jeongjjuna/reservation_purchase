package com.example.module_product_service.product.presentation.internal;

import com.example.module_product_service.product.application.ProductReadService;
import com.example.module_product_service.product.application.ProductService;
import com.example.module_product_service.product.domain.ProductStock;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/internal/reservation-product-stock")
public class ProductStockInternalApiController {

    private final ProductService productService;
    private final ProductReadService productReadService;

    /**
     * 예약 상품 조회
     */
    @GetMapping( "/{reservationProductId}")
    public ResponseEntity<ProductStock> findByReservationProductStockId(
            @PathVariable final Long reservationProductId
    ) {
        return ResponseEntity.ok(productReadService.readStockCount(reservationProductId));
    }

    /**
     * 예약 상품 재고 수량 변경
     */
    @PutMapping("/{reservationProductId}")
    public ResponseEntity<ProductStock> updateReservationProductStock(
            @PathVariable("reservationProductId") Long reservationProductId,
            @RequestBody final ProductStock productStock
    ) {
        return ResponseEntity.ok(productService.updateStock(reservationProductId, productStock));
    }
}
