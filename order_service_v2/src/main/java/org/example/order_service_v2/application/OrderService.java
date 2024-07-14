package org.example.order_service_v2.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.order_service_v2.domain.OrderCreate;
import org.example.order_service_v2.infrastructure.redis.RedisRepository;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private final RedisRepository redisRepository;

    public void create(final OrderCreate orderCreate) {
        // Long productId;
        // Long memberId;
        // Integer quantity;
        // String address;

        // 1. 유효성 검사
        // 1 - 1. 레디스에서 상품 판매 시작시간 정보를 가져온다. "products:34:reservation_start_at" = "시간"
        // 1 - 2. 시간보다 작으면 예외 발생
        // 1 - 3. 레디스에서 상품 재고 정보를 가져온다. "products:34:stocks" = "남은 개수"
        // 1 - 4. 상품 재고가 없으면 예외 발생

        // 2. 재고 감소 요청
        // 2 - 1. 레디스의 상품 재고를 감소시킴 "products:34:stocks" = "남은 개수" 감소

        // 3. 주문 발급 큐에 삽입
        // 3 -1. redis의 큐 자료구조에 주문발급
    }
}
