package com.example.activity_service.follow.presentation.internal;

import com.example.activity_service.common.response.Response;
import com.example.activity_service.follow.application.FollowService;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * 내가 팔로우한 사용자들의 리스트 반환
     */
    @GetMapping("/follows")
    public Response<List<Long>> findFollowing(
            @RequestParam(name = "member") final Long principalId
    ) {
        return Response.success(followService.findByFollowingId(principalId));
    }

    /**
     * 나를 팔로우한 사용자들의 리스트 반환
     */
    @GetMapping("/followers")
    public Response<List<Long>> findFollowers(
            @RequestParam(name = "member") final Long principalId
    ) {
        return Response.success(followService.findByFollowerId(principalId));
    }

}
