package com.example.module_order_service.order.infrastructure.repository.entity;

import com.example.module_order_service.order.domain.OrderHistory;
import com.example.module_order_service.order.domain.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@Entity
@Table(name = "order_history")
public class OrderHistoryEntity {

    @Id
    @Column(name = "orders_number", updatable = false)
    private Long orderId;

    @Column(name = "product_number", nullable = false)
    private Long productId;

    @Column(name = "member_number", nullable = false)
    private Long memberId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "address", nullable = false)
    private String address;

    @Enumerated(EnumType.STRING) // Enum 값을 문자열로 매핑
    @Column(name = "status", nullable = false)
    private Status status;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


    public static OrderHistoryEntity from(final OrderHistory orderHistory) {
        final OrderHistoryEntity orderHistoryEntity = new OrderHistoryEntity();
        orderHistoryEntity.orderId = orderHistory.getOrderId();
        orderHistoryEntity.productId = orderHistory.getProductId();
        orderHistoryEntity.memberId = orderHistory.getMemberId();
        orderHistoryEntity.quantity = orderHistory.getQuantity();
        orderHistoryEntity.price = orderHistory.getPrice();
        orderHistoryEntity.address = orderHistory.getAddress();
        orderHistoryEntity.status = orderHistory.getStatus();
        orderHistoryEntity.createdAt = orderHistory.getCreatedAt();
        return orderHistoryEntity;
    }

    public OrderHistory toModel() {
        return OrderHistory.builder()
                .orderId(orderId)
                .productId(productId)
                .memberId(memberId)
                .quantity(quantity)
                .price(price)
                .address(address)
                .status(status)
                .createdAt(createdAt)
                .build();
    }
}
