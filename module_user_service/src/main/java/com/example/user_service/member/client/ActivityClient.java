package com.example.user_service.member.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "activityClient", url = "${feign.activityClient.url}")
public interface ActivityClient {

    @RequestMapping(method = RequestMethod.GET, value = "/v1/internal/follows", consumes = "application/json")
    MemberList findFollowing(@RequestParam(name = "member") Long principalId);

    @RequestMapping(method = RequestMethod.GET, value = "/v1/internal/followers", consumes = "application/json")
    MemberList  findFollowers(@RequestParam(name = "member") Long principalId);
}
