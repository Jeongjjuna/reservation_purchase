package com.example.user_service.member.application;

import com.example.user_service.member.application.port.MemberRepository;
import com.example.user_service.member.client.ActivityClient;
import com.example.user_service.member.domain.Member;
import com.example.user_service.member.exception.MemberErrorCode;
import com.example.user_service.member.exception.MemberException.MemberNotFoundException;
import com.example.user_service.member.exception.MemberException.MemberUnauthorizedException;
import com.example.user_service.member.presentation.response.MemberResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MemberReadService {

    private final MemberRepository memberRepository;
    private final ActivityClient activityClient;

    public MemberReadService(final MemberRepository memberRepository, final ActivityClient activityClient) {
        this.memberRepository = memberRepository;
        this.activityClient = activityClient;
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
        List<Long> followingIds = activityClient.findFollowing(principalId).getData();

        // 1번의 select 발생
        Pageable pageable = PageRequest.of(0, 20);
        return memberRepository.findAllByIdIn(followingIds, pageable).map(MemberResponse::from);
    }

    /**
     * 나를 팔로우한 회원들 조회
     */
    public Page<MemberResponse> readMyFollowers(final Long principalId) {
        // 나를 팔로워한 모든 사람을 가져온다.
        List<Long> followerIds = activityClient.findFollowers(principalId).getData();

        Pageable pageable = PageRequest.of(0, 20);
        return memberRepository.findAllByIdIn(followerIds, pageable).map(MemberResponse::from);
    }
}
