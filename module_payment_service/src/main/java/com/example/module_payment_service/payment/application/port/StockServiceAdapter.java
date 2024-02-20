package com.example.module_payment_service.payment.application.port;

import com.example.module_payment_service.payment.domain.OrderStock;

public interface StockServiceAdapter {

    // 3. 재고수량을 +x 만큼해라
    void addStock(final Long productId, final OrderStock stockCount);

    // 4. 재고수량을 -x 만큼해라
    void subtract(final Long productId, final OrderStock stockCount);
}
