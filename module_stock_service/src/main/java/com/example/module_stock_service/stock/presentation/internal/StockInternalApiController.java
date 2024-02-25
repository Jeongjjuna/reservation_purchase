package com.example.module_stock_service.stock.presentation.internal;

import com.example.module_stock_service.stock.application.StockService;
import com.example.module_stock_service.stock.domain.Stock;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/internal/stock")
public class StockInternalApiController {

    private final StockService stockService;

    /**
     * 재고 생성
     */
    @PostMapping("/products/{productId}")
    public ResponseEntity<Stock> createStock(
            @PathVariable("productId") Long productId,
            @RequestBody final Stock productStock
    ) {
        return ResponseEntity.ok(stockService.create(productId, productStock));
    }

    /**
     * 재고 수량 변경
     */
    @PutMapping("/products/{productId}")
    public ResponseEntity<Stock> updateStock(
            @PathVariable("productId") Long productId,
            @RequestBody final Stock productStock
    ) {
        return ResponseEntity.ok(stockService.update(productId, productStock));
    }

    /**
     * 재고 수량 더하기
     */
    @PostMapping( "/increase/products/{productId}")
    public ResponseEntity addStock(
            @PathVariable final Long productId,
            @RequestBody final Stock productStock
    ) {
        stockService.add(productId, productStock);
        return ResponseEntity.ok().build();
    }

    /**
     * 재고 수량 빼기
     */
    @PostMapping("/decrease/products/{productId}")
    public ResponseEntity subtractStock(
            @PathVariable final Long productId,
            @RequestBody final Stock productStock
    ) {
        stockService.subtract(productId, productStock);
        return ResponseEntity.ok().build();
    }

}

