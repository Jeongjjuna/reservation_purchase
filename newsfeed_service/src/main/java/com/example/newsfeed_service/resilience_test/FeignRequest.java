package com.example.newsfeed_service.resilience_test;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "ResilienceNewsfeedFeignClient", url = "http://localhost:8081")
public interface FeignRequest {
    @RequestMapping(method = RequestMethod.GET, value = "/errorful/case1", consumes = "application/json")
    String case1();

    @RequestMapping(method = RequestMethod.GET, value = "/errorful/case2", consumes = "application/json")
    String case2();

    @RequestMapping(method = RequestMethod.GET, value = "/errorful/case3", consumes = "application/json")
    String case3();
}
