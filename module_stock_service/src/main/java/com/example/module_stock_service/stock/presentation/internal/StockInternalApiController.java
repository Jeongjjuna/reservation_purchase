package com.example.module_stock_service.stock.presentation.internal;

import com.example.module_stock_service.stock.application.StockService;
import com.example.module_stock_service.stock.domain.Stock;
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
public class StockInternalApiController {

    private final StockService stockService;

    /**
     * 상품 재고 조회
     */
    @GetMapping( "/{reservationProductId}")
    public ResponseEntity<Stock> findByReservationProductStockId(
            @PathVariable final Long reservationProductId
    ) {
        return ResponseEntity.ok(stockService.readStockCount(reservationProductId));
    }

    /**
     * 상품 재고수량 변경
     */
    @PutMapping("/{reservationProductId}")
    public ResponseEntity<Stock> updateReservationProductStock(
            @PathVariable("reservationProductId") Long reservationProductId,
            @RequestBody final Stock productStock
    ) {
        return ResponseEntity.ok(stockService.updateStock(reservationProductId, productStock));
    }
}

