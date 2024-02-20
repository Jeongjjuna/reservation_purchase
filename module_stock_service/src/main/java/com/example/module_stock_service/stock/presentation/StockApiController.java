package com.example.module_stock_service.stock.presentation;

import com.example.module_stock_service.common.response.Response;
import com.example.module_stock_service.stock.application.StockService;
import com.example.module_stock_service.stock.domain.Stock;
import com.example.module_stock_service.stock.presentation.response.StockResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/stocks")
public class StockApiController {

    private final StockService stockService;

    /**
     * 재고 수량 조회
     */
    @GetMapping("/products/{productId}")
    public Response<StockResponse> readStockCount(@PathVariable Long productId) {
        final Stock productStock = stockService.readStockCount(productId);
        return Response.success(StockResponse.from(productStock));
    }
}
