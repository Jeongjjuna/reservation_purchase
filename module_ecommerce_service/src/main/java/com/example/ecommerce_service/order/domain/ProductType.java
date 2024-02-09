package com.example.ecommerce_service.order.domain;

import java.util.Arrays;

public enum ProductType {
    PRODUCT("product"),
    RESERVATION_PRODUCT("reservationProduct");

    private String productType;

    ProductType(final String productType) {
        this.productType = productType;
    }

    public static ProductType create(final String productType) {
        return Arrays.stream(ProductType.values())
                .filter(value -> value.productType.equals(productType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] Unknown ProductType: " + productType));
    }
}
