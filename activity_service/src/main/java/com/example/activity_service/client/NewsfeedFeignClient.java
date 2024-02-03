package com.example.activity_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "newsfeedFeignClient", url = "${feign.newsfeedClient.url}")
public interface NewsfeedFeignClient {
    @RequestMapping(method = RequestMethod.POST, value = "/v1/newsfeeds", consumes = "application/json")
    void create(final NewsfeedCreate newsfeedCreate);
}
