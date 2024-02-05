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
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@AllArgsConstructor
@Slf4j
@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final NewsfeedFeignClient newsfeedFeignClient;
    private final CircuitBreakerFactory circuitBreakerFactory;
    private final RetryRegistry retryRegistry;

    /**
     * 팔로우 하기
     */
    @Transactional
    public void follow(final Long principalId, final FollowCreate followCreate) {

        Long followerMemberId = followCreate.getFollowerMemberId();
        Long followingMemberId = followCreate.getFollowingMemberId();

        checkAuthorized(followCreate.getFollowerMemberId(), principalId);
        checkDuplicated(followerMemberId, followingMemberId);

        Follow follow = Follow.create(followerMemberId, followingMemberId);
        followRepository.save(follow);

        NewsfeedCreate newsfeedCreate = follow.toNewsfeedCreate();

        // newsfeed_service 서비스에 팔로우 뉴스피드 생성 요청(feign client)
        sendNewsfeedRequest(newsfeedCreate); // TODO : 1. 분산 트랜잭션 체크 2. 테스트할때 mongodb 트랜잭션 체크
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

    private void checkDuplicated(final Long followerMemberId, Long followingMemberId) {
        followRepository.findByFollowerAndFollowing(followerMemberId, followingMemberId).ifPresent(it -> {
            throw new FollowDuplicatedException(FollowErrorCode.FOLLOW_DUPLICATED);
        });
    }

    private void checkAuthorized(final Long targetId, final Long principalId) {
        if (!principalId.equals(targetId)) {
            throw new FollowUnauthorizedException(FollowErrorCode.UNAUTHORIZED_ACCESS_ERROR);
        }
    }

    /**
     * TODO : 서킷브레이커, 리트라이의 공통 부분을 어떻게 리팩토링 해볼지 고민해보자.
     */
    private void sendNewsfeedRequest(final NewsfeedCreate newsfeedCreate) {
        final CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        final Retry retry = retryRegistry.retry("retry");
        circuitBreaker.run(() -> Retry.decorateFunction(retry, s -> {
            newsfeedFeignClient.create(newsfeedCreate);
            return "success";
        }).apply(1), throwable -> "failure");
    }
}
