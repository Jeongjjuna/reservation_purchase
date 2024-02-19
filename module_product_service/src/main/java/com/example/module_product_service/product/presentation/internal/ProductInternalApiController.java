package com.example.module_product_service.product.presentation.internal;

import com.example.module_product_service.product.application.ProductReadService;
import com.example.module_product_service.product.domain.Product;
import com.example.module_product_service.product.presentation.response.ProductResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/internal/reservation-products")
public class ProductInternalApiController {

    private final ProductReadService productReadService;

    /**
     * 상품 조회
     */
    @GetMapping( "/{reservationProductId}")
    public ResponseEntity<ProductResponse> findByReservationProductId(
            @PathVariable final Long reservationProductId
    ) {
        Product product = productReadService.find(reservationProductId);
        return ResponseEntity.ok(ProductResponse.from(product));
    }
}
