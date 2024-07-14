package org.example.order_service_v2.infrastructure.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;

@SpringBootTest
class RedisRepositoryTest {

    @Autowired
    private RedisRepository redisRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void test() throws JsonProcessingException {
        String key = "products:" + 34L + ":reservation_start_at";
        redisRepository.setnx(key, objectMapper.writeValueAsString(LocalDateTime.now()));
    }

    @Test
    void test2() throws JsonProcessingException {
        String key = "products:" + 34L + ":reservation_start_at";
        String time = redisRepository.get(key);

        LocalDateTime t = objectMapper.readValue(time, LocalDateTime.class);
        System.out.println(t);
        System.out.println(LocalDateTime.now());
    }

    @Test
    void test3() {
        System.out.println(redisRepository.get("invalid"));
    }

    @Test
    void test4() {
        String key = "products:%s:reservation_start_at".formatted(34L);
        System.out.println(key);
    }
}