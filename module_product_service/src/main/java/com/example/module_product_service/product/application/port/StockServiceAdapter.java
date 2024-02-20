package com.example.module_product_service.product.application.port;

import com.example.module_product_service.product.domain.ProductStock;

public interface StockServiceAdapter {

    // 1. 새로생긴 상품에 대한 재고를 생성한다.
    void createStockCount(final Long productId, final ProductStock productStock);

    // 2. 재고수량을 stockCount로 업데이트해라
    void updateStockCount(final Long productId, final ProductStock productStock);

    // 3. 재고수량을 +x 만큼해라
    void addStock(final Long productId, final ProductStock productStock);

    // 4. 재고수량을 -x 만큼해라
    void subtract(final Long productId, final ProductStock productStock);
}
