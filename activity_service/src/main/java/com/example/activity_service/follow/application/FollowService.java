package com.example.activity_service.follow.application;

import com.example.activity_service.client.NewsfeedCreate;
import com.example.activity_service.client.NewsfeedFeignClient;
import com.example.activity_service.follow.application.port.FollowRepository;
import com.example.activity_service.follow.domain.Follow;
import com.example.activity_service.follow.domain.FollowCreate;
import com.example.activity_service.follow.exception.FollowErrorCode;
import com.example.activity_service.follow.exception.FollowException.FollowDuplicatedException;
import com.example.activity_service.follow.exception.FollowException.FollowUnauthorizedException;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final NewsfeedFeignClient newsfeedFeignClient;
    private final CircuitBreakerFactory circuitBreakerFactory;
    private final RetryRegistry retryRegistry;

    public FollowService(
            final FollowRepository followRepository,
            final NewsfeedFeignClient newsfeedFeignClient,
            final CircuitBreakerFactory circuitBreakerFactory,
            final RetryRegistry retryRegistry
    ) {
        this.followRepository = followRepository;
        this.newsfeedFeignClient = newsfeedFeignClient;
        this.circuitBreakerFactory = circuitBreakerFactory;
        this.retryRegistry = retryRegistry;
    }

    /**
     * 팔로우 하기
     */
    @Transactional
    public void follow(final Long principalId, final FollowCreate followCreate) {

        // TODO : member 존재여부를 미리 인증 GATEWAY에서 받아야 하는가?
        // 만약 user_serivce에게 해당 유저가 존재하는지 확인 요청을 보내야 한다면
        // 아래에 user_service에 존재 하는지 요청하는 로직을 추가한다.(여러가지 근거들로 장/단점 따져봐야함)
        Long followerMemberId = followCreate.getFollowerMemberId();
        Long followingMemberId = followCreate.getFollowingMemberId();

        checkAuthorized(followCreate.getFollowerMemberId(), principalId);
        checkDuplicated(followCreate);

        Follow follow = Follow.create(followerMemberId, followingMemberId);
        Follow saved = followRepository.save(follow);

        /**
         * 뉴스피드에 좋아요 기록 추가
         * TODO : 1. 분산 트랜잭션 체크 2. 테스트할때 mongodb 트랜잭션 체크
         */
        NewsfeedCreate newsfeedCreate = NewsfeedCreate.builder()
                .receiverId(followerMemberId)
                .senderId(followingMemberId)
                .newsfeedType("follow")
                .activityId(saved.getId())
                .build();

        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        Retry retry = retryRegistry.retry("retry");
        circuitBreaker.run(() -> Retry.decorateFunction(retry, s -> {
            newsfeedFeignClient.create(newsfeedCreate);
            return "success";
        }).apply(1), throwable -> "failure");

    }

    public List<Long> findByFollowingId(final Long principalId) {
        return followRepository.findFollowing(principalId).stream()
                .map(Follow::getFollowingMemberId)
                .toList();
    }

    public List<Long> findByFollowerId(final Long principalId) {
        return followRepository.findFollower(principalId).stream()
                .map(Follow::getFollowerMemberId)
                .toList();
    }

    private void checkDuplicated(final FollowCreate followCreate) {
        Long followerMemberId = followCreate.getFollowerMemberId();
        Long followingMemberId = followCreate.getFollowingMemberId();

        followRepository.findByFollowerAndFollowing(followerMemberId, followingMemberId).ifPresent(it -> {
            throw new FollowDuplicatedException(FollowErrorCode.FOLLOW_DUPLICATED);
        });
    }

    private void checkAuthorized(Long targetId, Long principalId) {
        if (!principalId.equals(targetId)) {
            throw new FollowUnauthorizedException(FollowErrorCode.UNAUTHORIZED_ACCESS_ERROR);
        }
    }
}
