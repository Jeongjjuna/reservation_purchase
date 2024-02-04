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
     * TODO : followerId, followingId 에 대해 존재하는 id인지 검사는 어디서?
     * 이 요청은 API GATEWAY를 타고오는가? 아니면 user_service에서 요청해야 하는가?
     * user_service는 인증만을 위한 서버? 아니면 비즈니스 로직을 섞어도 되는가?
     */
    @PostMapping
    public Response<Void> follow(
            @RequestBody final FollowCreate followCreate,
            @RequestParam(name = "member", required = false) Long principalId
    ) {
        followService.follow(principalId, followCreate);
        return Response.success();
    }
}
