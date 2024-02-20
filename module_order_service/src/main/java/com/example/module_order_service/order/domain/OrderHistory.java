package com.example.module_order_service.order.domain;

import com.example.module_order_service.common.exception.GlobalException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

@Getter
public class OrderHistory {

    private Long orderId;
    private Long productId;
    private Long memberId;
    private Integer quantity;
    private Integer price;
    private String address;
    private Status status;
    private LocalDateTime createdAt;

    @Builder
    public OrderHistory(
            final Long orderId,
            final Long productId,
            final Long memberId,
            final Integer quantity,
            final Integer price,
            final String address,
            final Status status,
            final LocalDateTime createdAt
    ) {
        this.orderId = orderId;
        this.productId = productId;
        this.memberId = memberId;
        this.quantity = quantity;
        this.price = price;
        this.address = address;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static OrderHistory create(final Order order) {
        return OrderHistory.builder()
                .orderId(order.getId())
                .productId(order.getProductId())
                .memberId(order.getMemberId())
                .quantity(order.getQuantity())
                .price(order.getPrice())
                .address(order.getAddress())
                .status(Status.PROCESSING)
                .build();
    }

    public OrderHistory cancel() {
        if (this.status != Status.PROCESSING) {
            throw new GlobalException(HttpStatus.CONFLICT, "주문 진행중이 아닙니다. 취소할 수 없습니다.");
        }
        this.status = Status.CANCELED;
        return this;
    }

    public OrderHistory complete() {
        if (this.status != Status.PROCESSING) {
            throw new GlobalException(HttpStatus.CONFLICT, "주문 진행중이 아닙니다. 완료할 수 없습니다.");
        }
        this.status = Status.COMPLETED;
        return this;
    }
}
