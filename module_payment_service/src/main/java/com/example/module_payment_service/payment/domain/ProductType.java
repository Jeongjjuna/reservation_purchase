package com.example.module_payment_service.payment.domain;

import lombok.Getter;
import java.util.Arrays;

@Getter
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
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 타입입니다 : " + productType));
    }
}
