package com.example.reservation_purchase.member.application;

import com.example.reservation_purchase.member.application.port.MemberRepository;
import com.example.reservation_purchase.member.domain.Member;
import com.example.reservation_purchase.member.domain.MemberCreate;
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

        // TODO : 이메일 중복 검사
        // TODO : 암호화

        Member member = Member.create(memberCreate);

        Member saved = memberRepository.save(member);

        return MemberJoinResponse.from(saved);
    }
}
