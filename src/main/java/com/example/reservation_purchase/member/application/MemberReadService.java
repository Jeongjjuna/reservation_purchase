package com.example.reservation_purchase.member.application;

import com.example.reservation_purchase.follow.application.port.FollowRepository;
import com.example.reservation_purchase.follow.domain.Follow;
import com.example.reservation_purchase.member.application.port.MemberRepository;
import com.example.reservation_purchase.member.domain.Member;
import com.example.reservation_purchase.member.exception.MemberErrorCode;
import com.example.reservation_purchase.member.exception.MemberException.MemberNotFoundException;
import com.example.reservation_purchase.member.exception.MemberException.MemberUnauthorizedException;
import com.example.reservation_purchase.member.presentation.response.MemberResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MemberReadService {

    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    public MemberReadService(final MemberRepository memberRepository, final FollowRepository followRepository) {
        this.memberRepository = memberRepository;
        this.followRepository = followRepository;
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

    public Page<MemberResponse> readMyFollows(final Long principalId) {
        // 내가 팔로우한 모든 Follow 정보를 가져온다. (여기서 2번의 select 발생)
        List<Follow> fs = followRepository.findByFollowingMember(principalId);

        List<Long> followingIds = fs.stream()
                .map(Follow::getFollowingMember)
                .map(Member::getId)
                .toList();

        // 1번의 select 발생
        Pageable pageable = PageRequest.of(0, 20);
        return memberRepository.findAllByIdIn(followingIds, pageable).map(MemberResponse::from);
    }
}
