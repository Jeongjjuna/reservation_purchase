package com.example.newsfeed_service.newsfeed.presentation;

import com.example.newsfeed_service.newsfeed.application.NewsfeedService;
import com.example.newsfeed_service.newsfeed.domain.NewsfeedCreate;
import com.example.newsfeed_service.newsfeed.presentation.response.NewsfeedResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;

/*
  추후에 내부 서버들에서 호출할 예정이다.
  ex) 유저서비스 -> 뉴스피드 api 호출
      게시글/좋아요서비스 -> 뉴스피드 api 호출
 */
@Slf4j
@RestController
@RequestMapping("/v1/newsfeeds")
public class NewsfeedApiController {

    private final NewsfeedService newsfeedService;

    public NewsfeedApiController(final NewsfeedService newsfeedService) {
        this.newsfeedService = newsfeedService;
    }

    /**
     * 뉴스피드를 생성합니다.
     */
    @PostMapping
    public ResponseEntity<Void> create(
            @RequestBody final NewsfeedCreate newsfeedCreate
    ) {
        newsfeedService.create(newsfeedCreate);
        return ResponseEntity.created(URI.create("v1/newsfeeds/my")).build();
    }

    /**
     * 특정 회원의 뉴스피드를 보여준다.
     * API GATEWAY 에서 호출할 예정이고, 쿼리파라미터로 인증정보를 넘겨받는다.
     */
    // TODO : url을 어떻게 수정할 지 고민해보자. 다양한 사례들 찾아보기.
    @GetMapping
    public ResponseEntity<Page<NewsfeedResponse>> read(
            @RequestParam(name = "member", required = false) Long principalId
    ) {
        log.info("NewsfeedApiController read()호출 : 특정 회원의 뉴스피드 요청");
        Page<NewsfeedResponse> myNewsfeeds = newsfeedService.my(principalId).map(NewsfeedResponse::from);
        return ResponseEntity.ok(myNewsfeeds);
    }
}
