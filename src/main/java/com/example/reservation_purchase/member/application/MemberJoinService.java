package com.example.reservation_purchase.member.application;

import com.example.reservation_purchase.member.application.port.MemberRepository;
import com.example.reservation_purchase.member.domain.Member;
import com.example.reservation_purchase.member.domain.MemberCreate;
import com.example.reservation_purchase.member.exception.MemberErrorCode;
import com.example.reservation_purchase.member.exception.MemberException.MemberDuplicatedException;
import com.example.reservation_purchase.member.presentation.response.MemberJoinResponse;
import org.springframework.stereotype.Service;

@Service
public class MemberJoinService {

    private final MemberRepository memberRepository;

    public MemberJoinService(final MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public MemberJoinResponse join(final MemberCreate memberCreate) {

        memberCreate.validate();

        memberRepository.findByEmail(memberCreate.getEmail()).ifPresent(it -> {
            throw new MemberDuplicatedException(MemberErrorCode.MEMBER_DUPLICATED);
        });

        // TODO : 암호화

        Member member = Member.create(memberCreate);

        Member saved = memberRepository.save(member);

        return MemberJoinResponse.from(saved);
    }
}
