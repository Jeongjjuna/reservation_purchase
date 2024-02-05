package com.example.user_service.auth.application;

import com.example.user_service.auth.application.port.RedisRefreshRepository;
import com.example.user_service.auth.domain.RefreshTokenInfo;
import com.example.user_service.auth.presentation.response.RefreshResponse;
import com.example.user_service.auth.security.jwt.JwtTokenProvider;
import com.example.user_service.auth.security.jwt.TokenType;
import com.example.user_service.common.exception.GlobalException;
import com.example.user_service.member.application.port.MemberRepository;
import com.example.user_service.member.exception.MemberErrorCode;
import com.example.user_service.member.exception.MemberException.MemberNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RefreshTokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final RedisRefreshRepository redisRefreshRepository;

    public RefreshResponse refresh(final RefreshTokenInfo refreshTokenInfo, final String deviceUUID) {
        final String refreshToken = refreshTokenInfo.getRefreshToken();

        final String findRefresh = redisRefreshRepository.findByValue(deviceUUID);

        checkNull(findRefresh);
        checkSameToken(findRefresh, refreshTokenInfo);

        final String email = jwtTokenProvider.getEmail(refreshToken, TokenType.REFRESH);

        final String accessToken = memberRepository.findByEmail(email)
                .map(member -> jwtTokenProvider.generate(email, member.getName(), TokenType.ACCESS))
                .orElseThrow(() -> new MemberNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND));

        // TODO : 리프레쉬 기간이 얼마 안남으면 그냥 리프레쉬도 새로 발급해준다.(보류)
        return RefreshResponse.from(accessToken, refreshToken);
    }

    private void checkSameToken(final String findRefresh, final RefreshTokenInfo refreshTokenInfo) {
        if (!findRefresh.equals(refreshTokenInfo.getRefreshToken())) {
            throw new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] ] not correct refresh token");
        }
    }

    private void checkNull(final String findRefresh) {
        if (findRefresh == null) {
            throw new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] ] not found refresh token");
        }
    }
}
