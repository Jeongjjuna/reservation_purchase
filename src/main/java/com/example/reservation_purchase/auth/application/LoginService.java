package com.example.reservation_purchase.auth.application;

import com.example.reservation_purchase.auth.application.port.RefreshRepository;
import com.example.reservation_purchase.auth.domain.LoginInfo;
import com.example.reservation_purchase.auth.domain.TokenType;
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
    public LoginResponse login(LoginInfo loginInfo, final String deviceUUID) {
        String email = loginInfo.getEmail();
        String password = loginInfo.getPassword();

        Member member = findExistMember(email);

        checkPassword(password, member);

        String accessToken = jwtTokenProvider.generate(member.getEmail(), member.getName(), TokenType.ACCESS);
        String refreshToken = jwtTokenProvider.generate(member.getEmail(), member.getName(), TokenType.REFRESH);

        /**
         * redis에저장
         * device = {memberId1 + : +uuid1} - 자동만료가능
         * deviceToken = {uuid1 : refreshToken1} - 자동만료가능
         * memberLogins = {memberId : {uuid1, uuid2, . . .}} -> 위에가 자동만료되었을 때 어떻게 할 것인가?
         */
        long duration = jwtTokenProvider.getExpiredTime(refreshToken, TokenType.REFRESH);

        String memberId = String.valueOf(member.getId());
        String device = refreshRepository.findByValue(memberId + deviceUUID);
        // 이미 존재 하면 덮어쓰운다.
        if (device != null) {
            refreshRepository.save(memberId + "-" + deviceUUID, String.valueOf(member.getId()), duration);
            refreshRepository.save(deviceUUID, refreshToken, duration);
        } else {
            refreshRepository.save(memberId + "-" + deviceUUID, String.valueOf(member.getId()), duration);
            refreshRepository.save(deviceUUID, refreshToken, duration);
            refreshRepository.addToHash(memberId, deviceUUID, "NULL");
        }

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
