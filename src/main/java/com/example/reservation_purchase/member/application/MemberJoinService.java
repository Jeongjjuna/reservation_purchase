package com.example.reservation_purchase.member.application;

import com.example.reservation_purchase.member.application.port.MemberRepository;
import com.example.reservation_purchase.member.domain.Member;
import com.example.reservation_purchase.member.domain.MemberCreate;
import com.example.reservation_purchase.member.exception.MemberErrorCode;
import com.example.reservation_purchase.member.exception.MemberException.MemberDuplicatedException;
import com.example.reservation_purchase.member.presentation.response.MemberJoinResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberJoinService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public MemberJoinService(final MemberRepository memberRepository, final BCryptPasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public MemberJoinResponse join(final MemberCreate memberCreate) {
        memberCreate.validate();
        checkDuplicatedEmail(memberCreate.getEmail());

        // TODO : 인증번호와 함께 받아와서 시간안에 등록됐는지 확인한다.
        /*
          redis 에 {email, 마감최종시간, 비밀번호} 를 확인하여 가입을 진행한다.
         */

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
