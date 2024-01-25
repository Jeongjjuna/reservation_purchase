package com.example.reservation_purchase.member.application;

import com.example.reservation_purchase.member.application.port.MemberRepository;
import com.example.reservation_purchase.member.domain.Member;
import com.example.reservation_purchase.member.exception.MemberErrorCode;
import com.example.reservation_purchase.member.exception.MemberException;
import org.springframework.stereotype.Service;

@Service
public class MemberReadService {

    private final MemberRepository memberRepository;

    public MemberReadService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member read(final Long targetId, final Long principalId) {
        checkAuthorized(targetId, principalId);
        return findExistMember(targetId);
    }

    private void checkAuthorized(Long targetId, Long principalId) {
        if (targetId != principalId) {
            throw new MemberException.MemberUnauthorizedException(MemberErrorCode.UNAUTHORIZED_ACCESS_ERROR);
        }
    }

    private Member findExistMember(Long id) {
        return memberRepository.findById(id).orElseThrow(() ->
                new MemberException.MemberNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

}
