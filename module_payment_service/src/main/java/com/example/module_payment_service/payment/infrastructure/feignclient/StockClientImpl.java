package com.example.module_payment_service.payment.infrastructure.feignclient;

import com.example.module_payment_service.payment.application.port.StockServiceAdapter;
import com.example.module_payment_service.payment.domain.OrderStock;
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
    public void subtract(final Long productId, final OrderStock stockCount) {
        stockFeignClient.subtract(productId, stockCount);
    }
}
