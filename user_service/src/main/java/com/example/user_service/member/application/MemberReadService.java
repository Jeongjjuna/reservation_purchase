package com.example.user_service.member.application;

import com.example.user_service.member.application.port.MemberRepository;
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

    public MemberReadService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
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
        // 내가 팔로우한 모든 Follow 정보를 가져온다. (여기서 2번의 select 발생)
        // List<Follow> followings = followRepository.findFollowing(principalId);
//
//        List<Long> followingIds = followings.stream()
//                .map(Follow::getFollowingMember)
//                .map(Member::getId)
//                .toList();
        // TODO : activity service에서 principalId에 대항하는 모든 following ids를 가져온다.
        List<Long> followingIds = List.of();

        // 1번의 select 발생
        Pageable pageable = PageRequest.of(0, 20);
        return memberRepository.findAllByIdIn(followingIds, pageable).map(MemberResponse::from);
    }

    /**
     * 나를 팔로우한 회원들 조회
     */
    public Page<MemberResponse> readMyFollowers(final Long principalId) {
        // 나를 팔로우한 모든 Follow 정보를 가져온다. (여기서 2번의 select 발생)
        // List<Follow> followers = followRepository.findFollower(principalId);

        //        List<Long> followingIds = followers.stream()
        //                .map(Follow::getFollowerMember)
        //                .map(Member::getId)
        //                .toList();

        // TODO : activity service에서 principalId에 해당하는 모든 follower ids를 가져온다.
        List<Long> followerIds = List.of();

        Pageable pageable = PageRequest.of(0, 20);
        return memberRepository.findAllByIdIn(followerIds, pageable).map(MemberResponse::from);
    }
}
