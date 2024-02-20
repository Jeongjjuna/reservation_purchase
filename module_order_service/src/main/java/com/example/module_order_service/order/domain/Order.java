package com.example.module_order_service.order.domain;

import com.example.module_order_service.common.exception.GlobalException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Getter
public class Order {

    private Long id;
    private Long productId;
    private Long memberId;
    private Integer quantity;
    private Integer price;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    @Builder
    public Order(
            final Long id,
            final Long productId,
            final Long memberId,
            final Integer quantity,
            final Integer price,
            final String address,
            final LocalDateTime createdAt,
            final LocalDateTime deletedAt
    ) {
        this.id = id;
        this.productId = productId;
        this.memberId = memberId;
        this.quantity = quantity;
        this.price = price;
        this.address = address;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
    }

    public static Order create(final OrderCreate orderCreate, final Integer price) {
        return Order.builder()
                .productId(orderCreate.getProductId())
                .memberId(orderCreate.getMemberId())
                .quantity(orderCreate.getQuantity())
                .price(price)
                .address(orderCreate.getAddress())
                .build();
    }

    public Order cancel() {
        if (deletedAt != null) {
            throw new GlobalException(HttpStatus.CONFLICT, "이미 취소 되어 있는 상품을 또 취소할 수 없습니다.");
        }
        deletedAt = LocalDateTime.now();
        return this;
    }
}
