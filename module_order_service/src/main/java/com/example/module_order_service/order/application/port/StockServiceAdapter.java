package com.example.module_order_service.order.application.port;

import com.example.module_order_service.order.domain.OrderStock;

public interface StockServiceAdapter {

    // 3. 재고수량을 +x 만큼해라
    void addStock(final Long productId, final OrderStock stockCount);

    // 4. 재고수량을 -x 만큼해라
    void subtractStock(final Long productId, final OrderStock stockCount);
}
