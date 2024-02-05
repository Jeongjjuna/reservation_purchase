package com.example.user_service.member.application;

import com.example.user_service.auth.application.port.RedisMailRepository;
import com.example.user_service.member.application.port.MemberRepository;
import com.example.user_service.member.domain.Member;
import com.example.user_service.member.domain.MemberCreate;
import com.example.user_service.member.exception.MemberErrorCode;
import com.example.user_service.member.exception.MemberException.MemberDuplicatedException;
import com.example.user_service.member.presentation.response.MemberJoinResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class MemberJoinService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RedisMailRepository redisMailRepository;

    /**
     * 회원가입
     */
    @Transactional
    public MemberJoinResponse signup(final MemberCreate memberCreate) {

        memberCreate.validate();

        checkDuplicatedEmail(memberCreate.getEmail());

        checkAuthenticNumber(memberCreate);

        final Member member = Member.create(memberCreate);
        encodePassword(member);

        final Member saved = memberRepository.save(member);
        return MemberJoinResponse.from(saved);
    }

    /**
     * 이메일 인증번호 검사
     * - 이미 인증 유효기간이 만료된 경우 NPE -> InvalidAuthenticException
     * - 애초에 인증절차를 거치지 않은 경우 NPE -> InvalidAuthenticException
     */
    private void checkAuthenticNumber(final MemberCreate memberCreate) {
        final String authenticationNumber = redisMailRepository.getData(memberCreate.getEmail());
        memberCreate.checkAuthenticated(authenticationNumber);

        redisMailRepository.deleteData(memberCreate.getEmail());
    }

    private void checkDuplicatedEmail(final String email) {
        memberRepository.findByEmail(email).ifPresent(it -> {
            throw new MemberDuplicatedException(MemberErrorCode.MEMBER_DUPLICATED);
        });
    }

    private void encodePassword(final Member member) {
        final String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.applyEncodedPassword(encodedPassword);
    }
}
