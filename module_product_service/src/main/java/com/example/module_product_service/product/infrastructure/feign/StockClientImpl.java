package com.example.module_product_service.product.infrastructure.feign;


import com.example.module_product_service.product.application.port.StockServiceAdapter;
import com.example.module_product_service.product.domain.ProductStock;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class StockClientImpl implements StockServiceAdapter {

    private final StockFeignClient stockFeignClient;

    @Override
    public void createStockCount(final Long productId, final ProductStock productStock) {
        stockFeignClient.createStock(productId, productStock);
    }

    @Override
    public void updateStockCount(final Long productId, final ProductStock productStock) {
        stockFeignClient.updateStock(productId, productStock);
    }

    @Override
    public void addStock(final Long productId, final ProductStock productStock) {
        stockFeignClient.addStock(productId, productStock);
    }

    @Override
    public void subtract(final Long productId, final ProductStock productStock) {
        stockFeignClient.subtract(productId, productStock);
    }
}
