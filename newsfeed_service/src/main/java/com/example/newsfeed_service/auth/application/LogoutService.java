package com.example.newsfeed_service.auth.application;

import com.example.newsfeed_service.auth.domain.LogoutInfo;
import com.example.newsfeed_service.auth.application.port.RedisRefreshRepository;
import com.example.newsfeed_service.auth.exception.AuthErrorCode;
import com.example.newsfeed_service.auth.exception.AuthException.UnauthorizedException;
import com.example.newsfeed_service.auth.security.jwt.JwtTokenProvider;
import com.example.newsfeed_service.auth.security.jwt.TokenType;
import com.example.newsfeed_service.exception.GlobalException;
import com.example.newsfeed_service.member.application.port.MemberRepository;
import com.example.newsfeed_service.member.domain.Member;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;

@Service
public class LogoutService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisRefreshRepository redisRefreshRepository;
    private final MemberRepository memberRepository;

    public LogoutService(
            final JwtTokenProvider jwtTokenProvider,
            final RedisRefreshRepository redisRefreshRepository,
            final MemberRepository memberRepository
    ) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.redisRefreshRepository = redisRefreshRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * 현재 기기에서 로그아웃
     */
    @Transactional
    public void logout(final LogoutInfo logoutInfo, final String principalEmail, final String deviceUUID) {
        String refreshToken = logoutInfo.getRefreshToken();

        String email = jwtTokenProvider.getEmail(refreshToken, TokenType.REFRESH);

        checkAuthorized(email, principalEmail);

        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] User not found"));

        // 존재하는 리프레쉬 토큰 확인 후 제거
        String memberId = String.valueOf(member.getId());
        String device = redisRefreshRepository.findByValue(memberId + "-" + deviceUUID);
        if (device == null) {
            throw new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] ] not found refresh token");
        }
        redisRefreshRepository.delete(memberId + "-" + deviceUUID);
        redisRefreshRepository.delete(deviceUUID);
        redisRefreshRepository.removeFromHash(memberId, deviceUUID);
    }

    /**
     * 모든 기기에서 로그아웃
     */
    public void logoutAll(final LogoutInfo logoutInfo, final String principalEmail) {
        String refreshToken = logoutInfo.getRefreshToken();

        String email = jwtTokenProvider.getEmail(refreshToken, TokenType.REFRESH);

        checkAuthorized(email, principalEmail);

        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] User not found"));

        // 존재하는 모든 리프레쉬 토큰 확인 후 제거
        String memberId = String.valueOf(member.getId());
        // 존재하는 모든 uuid - refreshToken 가져오기
        Map<String, String> allDevice = redisRefreshRepository.getAllFromHash(memberId);
        for (String uuid : allDevice.keySet()) {
            redisRefreshRepository.delete(memberId + "-" + uuid);
            redisRefreshRepository.delete(uuid);
            redisRefreshRepository.removeFromHash(memberId, uuid);
        }
    }

    private void checkAuthorized(String email, String principalEmail) {
        if (!email.equals(principalEmail)) {
            throw new UnauthorizedException(AuthErrorCode.UNAUTHORIZED_ERROR);
        }
    }

}
