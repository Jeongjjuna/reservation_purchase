package com.example.newsfeed_service.newsfeed.presentation.internal;

import com.example.newsfeed_service.common.response.Response;
import com.example.newsfeed_service.newsfeed.application.NewsfeedService;
import com.example.newsfeed_service.newsfeed.domain.NewsfeedCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/internal/newsfeeds")
public class InternalNewsfeedApiController {

    private final NewsfeedService newsfeedService;

    public InternalNewsfeedApiController(final NewsfeedService newsfeedService) {
        this.newsfeedService = newsfeedService;
    }

    /**
     * 뉴스피드를 생성합니다.
     */
    @PostMapping
    public Response<Void> create(
            @RequestBody final NewsfeedCreate newsfeedCreate
    ) {
        newsfeedService.create(newsfeedCreate);
        return Response.success();
    }
}
