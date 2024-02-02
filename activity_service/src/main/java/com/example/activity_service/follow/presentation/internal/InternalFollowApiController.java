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
@RequestMapping("/v1/internal")
public class InternalFollowApiController {

    private final FollowService followService;

    public InternalFollowApiController(final FollowService followService) {
        this.followService = followService;
    }

    @GetMapping("/follows")
    public ResponseEntity<List<Long>> findFollowing(
            @RequestParam(name = "member") Long principalId
    ) {
        return ResponseEntity.ok(followService.findByFollowingId(principalId));
    }

    @GetMapping("/followers")
    public ResponseEntity<List<Long>> findFollowers(
            @RequestParam(name = "member") Long principalId
    ) {
        return ResponseEntity.ok(followService.findByFollowerId(principalId));
    }

}
