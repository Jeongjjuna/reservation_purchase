package com.example.module_product_service.reservation_product.presentation.internal;

import com.example.module_product_service.reservation_product.application.ReservationProductReadService;
import com.example.module_product_service.reservation_product.application.ReservationProductService;
import com.example.module_product_service.reservation_product.domain.ReservationProductStock;
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
@RequestMapping("/v1/internal/reservation-products")
public class ReservationProductStockInternalApiController {

    private final ReservationProductService reservationProductService;
    private final ReservationProductReadService reservationProductReadService;

    /**
     * 예약 상품 조회
     */
    @GetMapping( "/{reservationProductId}")
    public ResponseEntity<ReservationProductStock> findByReservationProductStockId(
            @PathVariable final Long reservationProductId
    ) {
        return ResponseEntity.ok(reservationProductReadService.readStockCount(reservationProductId));
    }

    /**
     * 예약 상품 재고 수량 변경
     */
    @PutMapping("/{reservationProductId}")
    public ResponseEntity<ReservationProductStock> updateReservationProductStock(
            @PathVariable("reservationProductId") Long reservationProductId,
            @RequestBody final ReservationProductStock reservationProductStock
    ) {
        return ResponseEntity.ok(reservationProductService.updateStock(reservationProductId, reservationProductStock));
    }
}
