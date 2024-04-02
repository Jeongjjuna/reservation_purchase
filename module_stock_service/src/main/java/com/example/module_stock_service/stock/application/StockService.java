package com.example.module_stock_service.stock.application;

import com.example.module_stock_service.common.exception.GlobalException;
import com.example.module_stock_service.stock.application.port.StockRepository;
import com.example.module_stock_service.stock.domain.Stock;
import com.example.module_stock_service.stock.infrastructure.repository.RedisStockRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.concurrent.TimeUnit;

@Slf4j
@AllArgsConstructor
@Service
public class StockService {

    private final RedissonClient redissonClient;

    private final StockRepository stockRepository;

    private final RedisStockRepository redisStockRepository;

    private final RedisTemplate<String, Integer> productStockRedisTemplate;

    /**
     * 재소 수량 조회
     */
    public Stock read(final Long productId) {

        try {
            /**
             * redis 에 재고 수량이 존재 하는 경우 값을 반환
             */
            if (redisStockRepository.hasKey(productId)) {
                int stockCount = redisStockRepository.getValue(productId);
                return Stock.builder()
                        .productId(productId)
                        .stockCount(stockCount)
                        .build();
            }

            /**
             * redis 에 재고 수량이 존재 하지 않는 다면 데이터베이스에서 redis 로 값을 로드
             */
            Stock preStock = stockRepository.findByProductIdForRead(productId)
                    .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] 해당 상품의 재고 정보를 찾을 수 없습니다."));
            redisStockRepository.setKey(productId, preStock);
            return preStock;
        } catch (Exception e){
            throw new IllegalArgumentException("redis 장애 발생");
        }
    }

    /**
     * 재고 생성(상품 최초 등록 시)
     */
    @Transactional
    public Stock create(final Long productId, final Stock productStock) {
        return stockRepository.save(productStock);
    }

    /**
     * 재소 수량 변경
     */
    @Transactional
    public Stock update(final Long reservationProductId, final Stock reservationProductStock) {
        // TODO : 임계영역 처리
        return stockRepository.findByProductId(reservationProductId)
                .map(productStock -> productStock.update(reservationProductStock.getStockCount()))
                .map(stockRepository::save)
                .orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] 해당 상품의 재고 정보를 찾을 수 없습니다."));
    }

    /**
     * 재소 수량 증가
     */
    @Transactional
    public void add(final Long productId, final Stock productStock) {
        synchronized (this) {
            try {
                Long incrementResult = redisStockRepository.increase(productId, productStock.getStockCount());
                log.info("재고 증가 후 : " + incrementResult);
            } catch (Exception e) {
                throw new IllegalArgumentException("redis 장애 발생");
            }
        }
    }

    /**
     * 재고 수량 감소
     * 1. 단일 서버 내에서 synchronized를 활용
     * 2. redis에서 재고수량 조회
     */
    @Transactional
    public void subtract(final Long productId, final Stock productStock) {
        synchronized (this) {
            try {
                if (redisStockRepository.decrease(productId, productStock.getStockCount()) < 0) {
                    Long restoreResult = redisStockRepository.increase(productId, productStock.getStockCount());
                    log.info("복구 후 : " + restoreResult);
                    throw new GlobalException(HttpStatus.CONFLICT, "[ERROR] 재고 수량이 부족 합니다.");
                }
            } catch (GlobalException e) {
                throw e;
            } catch (Exception e) {
                throw new IllegalArgumentException("redis 장애 발생");
            }
        }
    }

    /**
     * -------------------------------- 테스트 ----------------------------------
     */

    /**
     * 실패 케이스
     */
    public void addCaseFail(final Long productId, final Stock productStock) {
        try {
            int count = redisStockRepository.getValue(productId);
            redisStockRepository.setKey(productId, new Stock(productId, productStock.getStockCount() + count));
            log.info("재고 증가");
        } catch (Exception e) {
            throw new IllegalArgumentException("redis 장애 발생");
        }
    }

    /**
     * 원자적 연산
     */
    @Transactional
    public void addCase0(final Long productId, final Stock productStock) {
        try {
            Long incrementResult = redisStockRepository.increase(productId, productStock.getStockCount());
            log.info("재고 증가 후 : " + incrementResult);
        } catch (Exception e) {
            throw new IllegalArgumentException("redis 장애 발생");
        }
    }

    /**
     * redis 락킹(분산락)
     */
    @Transactional
    public void addCase1(final Long productId, final Stock productStock) {
        // 분산락
        RLock lock = redissonClient.getLock(productId.toString());
        try {
            boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS);

            if(!available) {
                log.info("락 획득 실패");
                return;
            }
            int count = redisStockRepository.getValue(productId);
            redisStockRepository.setKey(productId, new Stock(productId, productStock.getStockCount() + count));
            log.info("재고 증가");
        } catch (InterruptedException e) {
            throw new IllegalArgumentException("redis 장애 발생");
        } catch (GlobalException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException("redis 장애 발생");
        } finally {
            lock.unlock();
        }
    }

    /**
     * Lua script
     */
    @Transactional
    public void addCase2(final Long productId, final Stock productStock) {
        // Lua script
    }

    /**
     * redis 트랜잭션 -> 불가능
     */
    @Transactional
    public void addCase3(final Long productId, final Stock productStock) {
        // 트랜잭션 -> 불가능
        productStockRedisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(final RedisOperations redisOperations) throws DataAccessException {
                try {
                    redisOperations.watch(productId);
                    redisOperations.multi();
                    String key = "product_id:" + String.valueOf(productId);
                    int count = (Integer) redisOperations.opsForValue().get(key);
                    redisOperations.opsForValue().set(key, count + productStock.getStockCount());
                    log.info("재고 증가");
                } catch (Exception e) {
                    redisOperations.discard();
                    e.printStackTrace();
                }
                return redisOperations.exec();
            }
        });
    }

    @Transactional
    public void subtractCaseFail(final Long productId, final Stock productStock) {
        try {
            int count = redisStockRepository.getValue(productId);
            if (count < productStock.getStockCount()) {
                throw new GlobalException(HttpStatus.CONFLICT, "[ERROR] 재고 수량이 부족 합니다.");
            }
            redisStockRepository.setKey(productId, new Stock(productId, count - productStock.getStockCount()));
        } catch (GlobalException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException("redis 장애 발생");
        }
    }

    /**
     * Case0. redis의 INCR, DECR을 통한 원자적 연산
     */
    @Transactional
    public void subtractCase0(final Long productId, final Stock productStock) {
        try {
            if (redisStockRepository.decrease(productId, productStock.getStockCount()) < 0) {
                Long restoreResult = redisStockRepository.increase(productId, productStock.getStockCount());
                log.info("복구 후 : " + restoreResult);
                throw new GlobalException(HttpStatus.CONFLICT, "[ERROR] 재고 수량이 부족 합니다.");
            }
        } catch (GlobalException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException("redis 장애 발생");
        }
    }

    /**
     * Case1. redis의 분산락을 활용해서 동시성 제어
     */
    @Transactional
    public void subtractCase1(final Long productId, final Stock productStock) {
        RLock lock = redissonClient.getLock(productId.toString());
        try {
            boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS);

            if(!available) {
                log.info("락 획득 실패");
                return;
            }
            // 1. redis 조회  2. redis업데이트 3. redis저장
            int count = redisStockRepository.getValue(productId);
            if (count < productStock.getStockCount()) {
                throw new GlobalException(HttpStatus.CONFLICT, "[ERROR] 재고 수량이 부족 합니다.");
            }
            redisStockRepository.setKey(productId, new Stock(productId, count - productStock.getStockCount()));
        } catch (InterruptedException e) {
            throw new IllegalArgumentException("redis 장애 발생");
        } catch (GlobalException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException("redis 장애 발생");
        } finally {
            lock.unlock();
        }
    }

    /**
     * Case2. Lua script를 활용해서 원자적 연산
     */
    @Transactional
    public void subtractCase2(final Long productId, final Stock productStock) {
        // 감소
    }

    /**
     * Case3. redis의 트랜잭션을 활용하여 원자적 연산
     */
    @Transactional
    public void subtractCase3(final Long productId, final Stock productStock) {
        // 트랜잭션 -> 불가능
        productStockRedisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(final RedisOperations redisOperations) throws DataAccessException {
                try {
                    String key = "product_id:" + productId;
                    redisOperations.watch(key);
                    redisOperations.multi();
                    int count = (Integer) redisOperations.opsForValue().get(key); // 트랜잭션 내부에서는 반환값을 활용할 수 없다.
                    if (count < productStock.getStockCount()) {
                        throw new GlobalException(HttpStatus.CONFLICT, "[ERROR] 재고 수량이 부족 합니다.");
                    }
                    redisOperations.opsForValue().set(key, count - productStock.getStockCount());
                } catch (Exception e) {
                    redisOperations.discard();
                    e.printStackTrace();
                }
                return redisOperations.exec();
            }
        });
    }
}
