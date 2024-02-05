package com.example.user_service.member.application;

import com.example.user_service.member.application.port.MemberRepository;
import com.example.user_service.member.client.ActivityClient;
import com.example.user_service.member.client.MemberList;
import com.example.user_service.member.domain.Member;
import com.example.user_service.member.exception.MemberErrorCode;
import com.example.user_service.member.exception.MemberException.MemberNotFoundException;
import com.example.user_service.member.exception.MemberException.MemberUnauthorizedException;
import com.example.user_service.member.presentation.response.MemberResponse;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MemberReadService {

    private final MemberRepository memberRepository;
    private final ActivityClient activityClient;
    private final CircuitBreakerFactory circuitBreakerFactory;
    private final RetryRegistry retryRegistry;

    public MemberReadService(
            final MemberRepository memberRepository,
            final ActivityClient activityClient,
            final CircuitBreakerFactory circuitBreakerFactory,
            final RetryRegistry retryRegistry
    ) {
        this.memberRepository = memberRepository;
        this.activityClient = activityClient;
        this.circuitBreakerFactory = circuitBreakerFactory;
        this.retryRegistry = retryRegistry;
    }

    /**
     * 회원 단건 조회
     */
    public Member read(final Long targetId, final Long principalId) {
        checkAuthorized(targetId, principalId);
        return findExistMember(targetId);
    }

    private void checkAuthorized(Long targetId, Long principalId) {
        if (!targetId.equals(principalId)) {
            throw new MemberUnauthorizedException(MemberErrorCode.UNAUTHORIZED_ACCESS_ERROR);
        }
    }

    private Member findExistMember(Long id) {
        return memberRepository.findById(id).orElseThrow(() ->
                new MemberNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    /**
     * 내가 팔로우한 회원들 조회
     */
    public Page<MemberResponse> readMyFollowing(final Long principalId) {
        // 내가 팔로우한 모든 id를 가져온다.
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        Retry retry = retryRegistry.retry("retry");
        MemberList followings = circuitBreaker.run(
                () -> Retry.decorateFunction(retry, s -> activityClient.findFollowing(principalId)).apply(1),
                throwable -> MemberList.of()
        );

        // 1번의 select 발생
        Pageable pageable = PageRequest.of(0, 20);
        return memberRepository.findAllByIdIn(followings.getData(), pageable).map(MemberResponse::from);
    }

    /**
     * 나를 팔로우한 회원들 조회
     */
    public Page<MemberResponse> readMyFollowers(final Long principalId) {
        // 나를 팔로워한 모든 사람을 가져온다.
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create("circuitbreaker");
        Retry retry = retryRegistry.retry("retry");
        MemberList followers = circuitBreaker.run(
                () -> Retry.decorateFunction(retry, s -> activityClient.findFollowers(principalId)).apply(1),
                throwable -> MemberList.of()
        );

        Pageable pageable = PageRequest.of(0, 20);
        return memberRepository.findAllByIdIn(followers.getData(), pageable).map(MemberResponse::from);
    }
}
