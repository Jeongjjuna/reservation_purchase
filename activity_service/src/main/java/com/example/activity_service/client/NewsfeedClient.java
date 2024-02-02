package com.example.activity_service.client;

import com.example.activity_service.follow.domain.FollowNewsfeed;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "newsfeedClient", url = "http://localhost:8082")
public interface NewsfeedClient {

    @RequestMapping(method = RequestMethod.POST, value = "/v1/newsfeeds", consumes = "application/json")
    void create(FollowNewsfeed followNewsfeed);
}
