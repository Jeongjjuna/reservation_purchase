package com.example.newsfeed_service.newsfeed.application;

import com.example.newsfeed_service.newsfeed.application.port.NewsfeedRepository;
import com.example.newsfeed_service.newsfeed.client.ActivityClient;
import com.example.newsfeed_service.newsfeed.domain.Newsfeed;
import com.example.newsfeed_service.newsfeed.domain.NewsfeedCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
public class NewsfeedService {

    private final NewsfeedRepository newsfeedRepository;
    private final ActivityClient activityClient;

    public NewsfeedService(
            final NewsfeedRepository newsfeedRepository,
            final ActivityClient activityClient
    ) {
        this.newsfeedRepository = newsfeedRepository;
        this.activityClient = activityClient;
    }

    /**
     * 뉴스피드 생성
     * - 추후 특정 회원이 하는 동작에 따라 뉴스피드를 생성해주도록 내부 요청으로 사용된다.
     */
    @Transactional
    public void create(final NewsfeedCreate newsfeedCreate) {
        Newsfeed newsfeed = Newsfeed.create(newsfeedCreate);
        newsfeedRepository.save(newsfeed);
    }

    /**
     * 나의 뉴스피드 조회
     */
    public Page<Newsfeed> my(final Long principalId) {
        log.info("NewsfeedService my()호출 : 특정 회원의 뉴스피드 요청");

        // TODO : 진짜 만약 팔로우를 걸어놓은 사람이 엄청 많다면? 만명이상?
        // 내가 팔로우한 모든 사람들의 아이디를 가져온다.
        List<Long> followingIds = activityClient.findFollowing(principalId).getData();

        // TODO : 마지막으로 확인한 시점 이후로 최신 데이터를 가져온다.
        Pageable pageable = PageRequest.of(
                0,
                20,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );
        return newsfeedRepository.findAllByFollowingIds(followingIds, pageable);
    }
}
