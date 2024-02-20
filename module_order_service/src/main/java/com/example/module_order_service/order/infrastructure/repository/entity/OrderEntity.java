package com.example.module_order_service.order.infrastructure.repository.entity;

import com.example.module_order_service.order.domain.Order;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_number", updatable = false)
    private Long id;

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

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public static OrderEntity from(final Order order) {
        final OrderEntity orderEntity = new OrderEntity();
        orderEntity.id = order.getId();
        orderEntity.productId = order.getProductId();
        orderEntity.memberId = order.getMemberId();
        orderEntity.quantity = order.getQuantity();
        orderEntity.price = order.getPrice();
        orderEntity.address = order.getAddress();
        orderEntity.createdAt = order.getCreatedAt();
        orderEntity.deletedAt = order.getDeletedAt();
        return orderEntity;
    }

    public Order toModel() {
        return Order.builder()
                .id(id)
                .productId(productId)
                .memberId(memberId)
                .quantity(quantity)
                .price(price)
                .address(address)
                .createdAt(createdAt)
                .deletedAt(deletedAt)
                .build();
    }
}
