package com.example.reservation_purchase.follow.application;

import com.example.reservation_purchase.follow.application.port.FollowRepository;
import com.example.reservation_purchase.follow.domain.Follow;
import com.example.reservation_purchase.follow.domain.FollowRequest;
import com.example.reservation_purchase.follow.exception.FollowErrorCode;
import com.example.reservation_purchase.follow.exception.FollowException.FollowUnauthorizedException;
import com.example.reservation_purchase.member.application.port.MemberRepository;
import com.example.reservation_purchase.member.domain.Member;
import com.example.reservation_purchase.member.exception.MemberErrorCode;
import com.example.reservation_purchase.member.exception.MemberException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    public FollowService(final FollowRepository followRepository, final MemberRepository memberRepository) {
        this.followRepository = followRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void follow(final Long principalId, final FollowRequest followRequest) {

        if (principalId != followRequest.getFollowerMemberId()) {
            throw new FollowUnauthorizedException(FollowErrorCode.UNAUTHORIZED_ACCESS_ERROR);
        }

        // TODO : 추후에 데이터를 가져오지말고 있는지만 확인해보자.(최적화)
        Member followerMember = memberRepository.findById(followRequest.getFollowerMemberId())
                .orElseThrow(() -> new MemberException.MemberNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND));

        Member folloingMember = memberRepository.findById(followRequest.getFollowingMemberId())
                .orElseThrow(() -> new MemberException.MemberNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND));

        System.out.println("aa");
        Follow follow = Follow.create(followerMember, folloingMember);
        followRepository.save(follow);
        System.out.println("bb");

        // TODO : 팔로우 했다고 folloingMember 피드에 등록해줘야 한다.
    }
}
