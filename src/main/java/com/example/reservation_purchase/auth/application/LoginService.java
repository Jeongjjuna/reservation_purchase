package com.example.reservation_purchase.auth.application;

import com.example.reservation_purchase.auth.domain.LoginInfo;
import com.example.reservation_purchase.auth.exception.AuthErrorCode;
import com.example.reservation_purchase.auth.exception.AuthException.InvalidPasswordException;
import com.example.reservation_purchase.member.application.port.MemberRepository;
import com.example.reservation_purchase.member.domain.Member;
import com.example.reservation_purchase.member.exception.MemberErrorCode;
import com.example.reservation_purchase.member.exception.MemberException.MemberNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    public LoginService(
            final MemberRepository memberRepository,
            final JwtTokenProvider jwtTokenProvider,
            final BCryptPasswordEncoder passwordEncoder
    ) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 로그인
     */
    public String login(LoginInfo loginInfo) {
        String email = loginInfo.getEmail();
        String password = loginInfo.getPassword();

        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new MemberNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND));

        if (isPasswordMatching(password, member)) {
            return jwtTokenProvider.generate(member.getEmail(), member.getName());
        }

        throw new InvalidPasswordException(AuthErrorCode.INVALID_PASSWORD_ERROR);
    }

    private boolean isPasswordMatching(String password, Member member) {
        return passwordEncoder.matches(password, member.getPassword());
    }
}
