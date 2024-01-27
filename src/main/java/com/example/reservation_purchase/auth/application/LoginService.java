package com.example.reservation_purchase.auth.application;

import com.example.reservation_purchase.auth.application.port.RefreshRepository;
import com.example.reservation_purchase.auth.domain.LoginInfo;
import com.example.reservation_purchase.auth.exception.AuthErrorCode;
import com.example.reservation_purchase.auth.exception.AuthException.InvalidPasswordException;
import com.example.reservation_purchase.auth.presentation.response.LoginResponse;
import com.example.reservation_purchase.member.application.port.MemberRepository;
import com.example.reservation_purchase.member.domain.Member;
import com.example.reservation_purchase.member.exception.MemberErrorCode;
import com.example.reservation_purchase.member.exception.MemberException.MemberNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RefreshRepository refreshRepository;

    public LoginService(
            final MemberRepository memberRepository,
            final JwtTokenProvider jwtTokenProvider,
            final BCryptPasswordEncoder passwordEncoder,
            final RefreshRepository refreshRepository) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.refreshRepository = refreshRepository;
    }

    /**
     * 로그인
     */
    @Transactional
    public LoginResponse login(LoginInfo loginInfo) {
        String email = loginInfo.getEmail();
        String password = loginInfo.getPassword();

        Member member = findExistMember(email);

        checkPassword(password, member);

        String accessToken = jwtTokenProvider.generateAccess(member.getEmail(), member.getName());
        String refreshToken = jwtTokenProvider.generateRefresh(member.getEmail(), member.getName());

        /**
         * 만약 memberId값이 RefreshToken 테이블에 이미 존재하는데 추가한다면?
         * -> 여러 기기에서 로그인 하고 있는 것과 같다.
         */
        // TODO : redis에 저장하도록 변경하자
        refreshRepository.save(member.getId(), refreshToken);

        return LoginResponse.from(accessToken, refreshToken);
    }

    private void checkPassword(final String password, final Member member) {
        if (isInvalidPassword(password, member)) {
            throw new InvalidPasswordException(AuthErrorCode.INVALID_PASSWORD_ERROR);
        }
    }

    private Member findExistMember(final String email) {
        return memberRepository.findByEmail(email).orElseThrow(() ->
                new MemberNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    private boolean isInvalidPassword(final String password, final Member member) {
        return !passwordEncoder.matches(password, member.getPassword());
    }
}
