package com.example.newsfeed_service.follow.presentation;

import com.example.newsfeed_service.auth.security.UserDetailsImpl;
import com.example.newsfeed_service.follow.application.FollowService;
import com.example.newsfeed_service.follow.domain.FollowCreate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/follow")
public class FollowApiController {

    private final FollowService followService;

    public FollowApiController(final FollowService followService) {
        this.followService = followService;
    }

    /**
     * 팔로우 하기
     */
    @PostMapping
    public ResponseEntity<Void> follow(
            @RequestBody final FollowCreate followCreate,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        followService.follow(userDetails.getId(), followCreate);
        return ResponseEntity.ok().build();
    }
}
