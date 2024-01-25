package com.example.reservation_purchase.member.application;

import com.example.reservation_purchase.member.application.port.MemberRepository;
import com.example.reservation_purchase.member.domain.Member;
import com.example.reservation_purchase.member.domain.MemberCreate;
import com.example.reservation_purchase.member.exception.MemberErrorCode;
import com.example.reservation_purchase.member.exception.MemberException.MemberDuplicatedException;
import com.example.reservation_purchase.member.presentation.response.MemberJoinResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberJoinService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public MemberJoinService(final MemberRepository memberRepository, final BCryptPasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public MemberJoinResponse join(final MemberCreate memberCreate) {
        memberCreate.validate();
        checkDuplicatedEmail(memberCreate.getEmail());

        Member member = Member.create(memberCreate);
        encodePassword(member);

        Member saved = memberRepository.save(member);
        return MemberJoinResponse.from(saved);
    }

    private void checkDuplicatedEmail(String email) {
        memberRepository.findByEmail(email).ifPresent(it -> {
            throw new MemberDuplicatedException(MemberErrorCode.MEMBER_DUPLICATED);
        });
    }

    private void encodePassword(Member member) {
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.applyEncodedPassword(encodedPassword);
    }
}
