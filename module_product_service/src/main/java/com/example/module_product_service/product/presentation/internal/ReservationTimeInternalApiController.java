package com.example.module_product_service.product.presentation.internal;

import com.example.module_product_service.product.application.ProductReadService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/internal/reservation-time")
public class ReservationTimeInternalApiController {

    private final ProductReadService productReadService;

    @GetMapping("/products/{productId}")
    public ResponseEntity<Boolean> isAfterReservationStartAt(
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok().body(productReadService.isAfterReservationStartAt(productId));
    }
}
