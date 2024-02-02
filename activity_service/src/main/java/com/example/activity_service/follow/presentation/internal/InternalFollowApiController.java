package com.example.activity_service.follow.presentation.internal;

import com.example.activity_service.follow.application.FollowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/internal/follows")
public class InternalFollowApiController {

    private final FollowService followService;

    public InternalFollowApiController(final FollowService followService) {
        this.followService = followService;
    }

    @GetMapping
    public ResponseEntity<List<Long>> findFollowing(
            @RequestParam(name = "member") Long principalId
    ) {
        log.info("[내부 internal 요청] InternalFollowApiController findFollowing() : 특정 회원이 팔로우한 모든 회원ID조회");
        return ResponseEntity.ok(followService.findByFollowerId(principalId));
    }

}
