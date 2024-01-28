package com.example.reservation_purchase.auth.application;

import com.example.reservation_purchase.auth.application.port.RefreshRepository;
import com.example.reservation_purchase.auth.domain.LogoutInfo;
import com.example.reservation_purchase.auth.domain.TokenType;
import com.example.reservation_purchase.auth.exception.AuthErrorCode;
import com.example.reservation_purchase.auth.exception.AuthException.UnauthorizedException;
import com.example.reservation_purchase.exception.GlobalException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LogoutService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshRepository refreshRepository;

    public LogoutService(final JwtTokenProvider jwtTokenProvider, final RefreshRepository refreshRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshRepository = refreshRepository;
    }

    /**
     * 로그아웃
     */
    @Transactional
    public void logout(final LogoutInfo logoutInfo, final String principalEmail) {
        String refreshToken = logoutInfo.getRefreshToken();

        String email = jwtTokenProvider.getEmail(refreshToken, TokenType.REFRESH);

        checkAuthorized(email, principalEmail);

        // 존재하는 리프레쉬 토큰 확인 후 제거
        String memberId = refreshRepository.findByValue(refreshToken);
        if (memberId == null) {
            throw new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] ] not found refresh token");
        }

        refreshRepository.delete(refreshToken);
    }

    private void checkAuthorized(String email, String principalEmail) {
        if (!email.equals(principalEmail)) {
            throw new UnauthorizedException(AuthErrorCode.UNAUTHORIZED_ERROR);
        }
    }
}
