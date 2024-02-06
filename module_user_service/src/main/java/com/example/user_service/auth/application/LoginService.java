package com.example.user_service.auth.application;

import com.example.user_service.auth.application.port.RedisRefreshRepository;
import com.example.user_service.auth.domain.LoginInfo;
import com.example.user_service.auth.exception.AuthErrorCode;
import com.example.user_service.auth.exception.AuthException.InvalidPasswordException;
import com.example.user_service.auth.presentation.response.LoginResponse;
import com.example.user_service.auth.security.jwt.JwtTokenProvider;
import com.example.user_service.auth.security.jwt.TokenType;
import com.example.user_service.member.application.port.MemberRepository;
import com.example.user_service.member.domain.Member;
import com.example.user_service.member.exception.MemberErrorCode;
import com.example.user_service.member.exception.MemberException.MemberNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class LoginService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RedisRefreshRepository redisRefreshRepository;


    /**
     * 로그인 -> redis에저장
     * device = {memberId1 + : +uuid1} - 자동만료가능
     * deviceToken = {uuid1 : refreshToken1} - 자동만료가능
     * memberLogins = {memberId : {uuid1, uuid2, . . .}} -> 위에가 자동만료되었을 때 어떻게 할 것인가?
     */
    @Transactional
    public LoginResponse login(final LoginInfo loginInfo, final String deviceUUID) {
        final String email = loginInfo.getEmail();
        final String password = loginInfo.getPassword();

        final Member member = findExistMember(email);

        checkPassword(password, member);

        final String accessToken = jwtTokenProvider.generate(member.getId(), member.getEmail(), TokenType.ACCESS);
        final String refreshToken = jwtTokenProvider.generate(member.getId(), member.getEmail(), TokenType.REFRESH);

        final long duration = jwtTokenProvider.getExpiredTime(refreshToken, TokenType.REFRESH);

        final String memberId = String.valueOf(member.getId());
        final String device = redisRefreshRepository.findByValue(memberId + deviceUUID);

        // 이미 존재 하면 덮어쓰운다.
        redisRefreshRepository.save(memberId + "-" + deviceUUID, String.valueOf(member.getId()), duration);
        redisRefreshRepository.save(deviceUUID, refreshToken, duration);
        if (device == null) {
            redisRefreshRepository.addToHash(memberId, deviceUUID, "NULL");
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
