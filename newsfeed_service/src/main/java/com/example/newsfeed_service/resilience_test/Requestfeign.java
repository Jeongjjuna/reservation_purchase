package com.example.newsfeed_service.resilience_test;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Requestfeign {

    private final FeignRequest feignRequest;

    public Requestfeign(final FeignRequest feignRequest) {
        this.feignRequest = feignRequest;
    }

    @GetMapping("/errorful/case1")
    public ResponseEntity<String> case1() {
        feignRequest.case1();
        return ResponseEntity.ok("Case1 success response");
    }

    @GetMapping("/errorful/case2")
    public ResponseEntity<String> case2() {
        feignRequest.case2();
        return ResponseEntity.ok("Case2 success response");
    }

    @GetMapping("/errorful/case3")
    public ResponseEntity<String> case3() {
        feignRequest.case3();
        return ResponseEntity.ok("Case3 success response");
    }
}
