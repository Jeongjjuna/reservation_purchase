package com.example.user_service.auth.application;

import com.example.user_service.auth.application.port.RedisRefreshRepository;
import com.example.user_service.auth.domain.LogoutInfo;
import com.example.user_service.auth.exception.AuthErrorCode;
import com.example.user_service.auth.exception.AuthException.UnauthorizedException;
import com.example.user_service.auth.security.jwt.JwtTokenProvider;
import com.example.user_service.auth.security.jwt.TokenType;
import com.example.user_service.common.exception.GlobalException;
import com.example.user_service.member.application.port.MemberRepository;
import com.example.user_service.member.domain.Member;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;

@AllArgsConstructor
@Service
public class LogoutService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisRefreshRepository redisRefreshRepository;
    private final MemberRepository memberRepository;

    /**
     * 현재 기기에서 로그아웃
     */
    @Transactional
    public void logout(final LogoutInfo logoutInfo, final String principalEmail, final String deviceUUID) {
        final String refreshToken = logoutInfo.getRefreshToken();

        final String email = jwtTokenProvider.getEmail(refreshToken, TokenType.REFRESH);

        checkAuthorized(email, principalEmail);

        final Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] User not found"));

        // 존재하는 리프레쉬 토큰 확인 후 제거
        final String memberId = String.valueOf(member.getId());
        final String device = redisRefreshRepository.findByValue(memberId + "-" + deviceUUID);
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
