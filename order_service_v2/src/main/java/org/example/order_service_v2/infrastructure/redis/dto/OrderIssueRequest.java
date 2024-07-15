package org.example.order_service_v2.infrastructure.redis.dto;

public record OrderIssueRequest(Long productId, Long memberId, Integer quantity, String address) {
}
