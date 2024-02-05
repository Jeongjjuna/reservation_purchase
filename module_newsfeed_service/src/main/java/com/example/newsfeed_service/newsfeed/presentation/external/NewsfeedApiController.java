package com.example.newsfeed_service.newsfeed.presentation.external;

import com.example.newsfeed_service.common.response.Response;
import com.example.newsfeed_service.newsfeed.application.NewsfeedService;
import com.example.newsfeed_service.newsfeed.presentation.response.NewsfeedResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/v1/newsfeeds")
public class NewsfeedApiController {

    private final NewsfeedService newsfeedService;

    public NewsfeedApiController(final NewsfeedService newsfeedService) {
        this.newsfeedService = newsfeedService;
    }

    /**
     * 특정 회원의 뉴스피드를 보여준다.
     * API GATEWAY 에서 호출할 예정이고, 쿼리파라미터로 인증정보를 넘겨받는다.
     */
    // TODO : url을 어떻게 수정할 지 고민해보자. 다양한 사례들 찾아보기.
    @GetMapping
    public Response<Page<NewsfeedResponse>> read(
            @RequestParam(name = "member", required = false) Long principalId
    ) {
        log.info("NewsfeedApiController read()호출 : 특정 회원의 뉴스피드 요청");
        Page<NewsfeedResponse> myNewsfeeds = newsfeedService.my(principalId).map(NewsfeedResponse::from);
        return Response.success(myNewsfeeds);
    }
}
