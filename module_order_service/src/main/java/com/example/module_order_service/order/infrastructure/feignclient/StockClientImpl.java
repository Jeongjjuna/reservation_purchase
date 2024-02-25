package com.example.module_order_service.order.infrastructure.feignclient;

import com.example.module_order_service.order.application.port.StockServiceAdapter;
import com.example.module_order_service.order.domain.OrderStock;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class StockClientImpl implements StockServiceAdapter {

    private final StockFeignClient stockFeignClient;

    @Override
    public void addStock(final Long productId, final OrderStock stockCount) {
        stockFeignClient.addStock(productId, stockCount);
    }

    @Override
    public void subtractStock(final Long productId, final OrderStock stockCount) {
        stockFeignClient.subtractStock(productId, stockCount);
    }
}
