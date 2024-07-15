package org.example.order_service_v2.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.order_service_v2.common.exception.GlobalException;
import org.example.order_service_v2.component.DistributeLockExecutor;
import org.example.order_service_v2.domain.OrderCreate;
import org.example.order_service_v2.infrastructure.mysql.ReservationTimeRepository;
import org.example.order_service_v2.infrastructure.mysql.StockRepository;
import org.example.order_service_v2.infrastructure.mysql.entity.ReservationTimeEntity;
import org.example.order_service_v2.infrastructure.mysql.entity.StockEntity;
import org.example.order_service_v2.infrastructure.redis.RedisRepository;
import org.example.order_service_v2.infrastructure.redis.dto.OrderIssueRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private final ReservationTimeRepository reservationTimeRepository;
    private final StockRepository stockRepository;
    private final RedisRepository redisRepository;
    private final ObjectMapper objectMapper;
    private final DistributeLockExecutor distributeLockExecutor;

    public void create(final OrderCreate orderCreate) {
        Long productId = orderCreate.getProductId();

        // 1. 유효성 검사

        // 1 - 1. 레디스에서 상품 판매 시작시간 정보를 가져온다. "products:34:reservation_start_at" = "시간"
        // 1 - 2. 시간보다 작으면 예외 발생
        try {
            String key = "products:%s:reservation_start_at".formatted(productId);

            String reservationStartAt = redisRepository.get(key);
            if (reservationStartAt == null) {
                ReservationTimeEntity reservationTime = reservationTimeRepository.findByProductId(productId)
                        .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] 상품 예약 시간을 찾을 수 없음."));

                reservationStartAt = objectMapper.writeValueAsString(reservationTime.getReservationStartAt());
                redisRepository.set(key, reservationStartAt);
            }

            LocalDateTime startAt = objectMapper.readValue(reservationStartAt, LocalDateTime.class);
            if (startAt.isAfter(LocalDateTime.now())) {
                throw new GlobalException(HttpStatus.CONFLICT, "[ERROR] 상품 판매 전 입니다");
            }
        } catch (Exception e) {
            throw new GlobalException(HttpStatus.INTERNAL_SERVER_ERROR, "[ERROR] 상품 정보 조회 에러");
        }

        distributeLockExecutor.execute("lock_%s".formatted(orderCreate.getProductId()), 3000, 3000, () -> {
            // 1 - 3. 레디스에서 상품 재고 정보를 가져온다. "products:34:stocks" = "남은 개수"
            // 1 - 4. 상품 재고가 없으면 예외 발생
            try {
                String key = "products:%s:stocks".formatted(productId);
                String stockCount = redisRepository.get(key);
                if (stockCount == null) {
                    StockEntity stock = stockRepository.findByProductId(productId)
                            .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] 상품 재고 정보를 찾을 수 없음"));

                    stockCount = objectMapper.writeValueAsString(stock.getStockCount());
                    redisRepository.set(key, stockCount);
                }

                Long stock = objectMapper.readValue(stockCount, Long.class);
                if (stock <= 0) {
                    throw new GlobalException(HttpStatus.CONFLICT, "[ERROR] 재고 수량이 부족합니다.");
                }
            }catch (Exception e) {
                throw new GlobalException(HttpStatus.INTERNAL_SERVER_ERROR, "[ERROR] 재고 수량 조회 에러");
            }


            // 2. 재고 감소 요청
            // 2 - 1. 레디스의 상품 재고를 감소시킴 "products:34:stocks" = "남은 개수" 감소
            String key = "products:%s:stocks".formatted(productId);
            redisRepository.decr(key);

            // 3. 주문 발급 큐에 삽입
            // 3 -1. redis의 큐 자료구조에 주문발급
            issueRequest(productId, orderCreate.getMemberId(), orderCreate.getQuantity(), orderCreate.getAddress());
        });
    }

    public void issueRequest(Long productId, Long memberId, Integer quantity, String address) {
        String issueRequestKey = "issue.request.productId:%s".formatted(productId);
        OrderIssueRequest orderIssueRequest = new OrderIssueRequest(productId, memberId, quantity, address);

        try {
            String value = objectMapper.writeValueAsString(orderIssueRequest);
            redisRepository.rPush(issueRequestKey, value);
        } catch (JsonProcessingException e) {
            throw new GlobalException(HttpStatus.INTERNAL_SERVER_ERROR, "[ERROR] 주문큐에 발급 중 에러");
        }
    }
}
