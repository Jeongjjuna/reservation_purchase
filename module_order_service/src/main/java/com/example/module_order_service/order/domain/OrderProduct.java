package com.example.module_order_service.order.domain;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class OrderProduct {

    private String name;
    private String content;
    private Long price;
    private LocalDateTime purchaseButtonActivationAt;

    public OrderProduct(final String name, final String content, final Long price, final LocalDateTime purchaseButtonActivationAt) {
        this.name = name;
        this.content = content;
        this.price = price;
        this.purchaseButtonActivationAt = purchaseButtonActivationAt;
    }
}
