package com.example.activity_service.follow.presentation.external;

import com.example.activity_service.common.response.Response;
import com.example.activity_service.follow.application.FollowService;
import com.example.activity_service.follow.domain.FollowCreate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/follows")
public class FollowApiController {

    private final FollowService followService;

    public FollowApiController(final FollowService followService) {
        this.followService = followService;
    }

    /**
     * 팔로우 하기
     */
    @PostMapping
    public Response<Void> follow(
            @RequestBody final FollowCreate followCreate,
            @RequestParam(name = "member") final Long principalId
    ) {
        followService.follow(principalId, followCreate);
        return Response.success();
    }
}
