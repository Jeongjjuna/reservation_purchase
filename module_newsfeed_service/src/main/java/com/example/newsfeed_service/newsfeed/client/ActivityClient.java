package com.example.newsfeed_service.newsfeed.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "activityClient", url = "${feign.activityClient.url}")
public interface ActivityClient {

    /**
     * activity_service에다가 내가 팔로우한 모든 회원id를 요청한다.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/v1/internal/follows", consumes = "application/json")
    FollowingIdList findFollowing(@RequestParam(name = "member") Long principalId);
}
