package com.example.reservation_purchase.member.application;

import com.example.reservation_purchase.member.application.port.MemberRepository;
import com.example.reservation_purchase.member.domain.Member;
import com.example.reservation_purchase.member.domain.MemberCreate;
import com.example.reservation_purchase.member.exception.MemberErrorCode;
import com.example.reservation_purchase.member.exception.MemberException.MemberDuplicatedException;
import com.example.reservation_purchase.member.presentation.response.MemberJoinResponse;
import com.example.reservation_purchase.util.RedisUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberJoinService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RedisUtil redisUtil;

    public MemberJoinService(final MemberRepository memberRepository, final BCryptPasswordEncoder passwordEncoder, final RedisUtil redisUtil) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.redisUtil = redisUtil;
    }

    @Transactional
    public MemberJoinResponse join(final MemberCreate memberCreate) {

        memberCreate.validate();

        checkDuplicatedEmail(memberCreate.getEmail());

        checkAuthenticNumber(memberCreate);

        Member member = Member.create(memberCreate);
        encodePassword(member);

        Member saved = memberRepository.save(member);
        return MemberJoinResponse.from(saved);
    }

    /*
      현재 인증 진행중인지 확인
       -> 1. 이미 인증 유효기간이 만료된 경우 NPE -> InvalidAuthenticException
       -> 2. 애초에 인증절차를 거치지 않은 경우 NPE -> InvalidAuthenticException
    */
    private void checkAuthenticNumber(final MemberCreate memberCreate) {
        String authenticationNumber = redisUtil.getData(memberCreate.getEmail());
        memberCreate.checkAuthenticated(authenticationNumber);

        redisUtil.deleteData(memberCreate.getEmail());
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
