package com.example.reservation_purchase.auth.application;

import com.example.reservation_purchase.auth.application.port.RefreshRepository;
import com.example.reservation_purchase.auth.domain.LogoutInfo;
import com.example.reservation_purchase.auth.domain.TokenType;
import com.example.reservation_purchase.auth.exception.AuthErrorCode;
import com.example.reservation_purchase.auth.exception.AuthException.UnauthorizedException;
import com.example.reservation_purchase.exception.GlobalException;
import com.example.reservation_purchase.member.application.port.MemberRepository;
import com.example.reservation_purchase.member.domain.Member;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LogoutService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshRepository refreshRepository;
    private final MemberRepository memberRepository;

    public LogoutService(final JwtTokenProvider jwtTokenProvider, final RefreshRepository refreshRepository, final MemberRepository memberRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshRepository = refreshRepository;
        this.memberRepository = memberRepository;
    }

    /**
     * 로그아웃
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
        String device = refreshRepository.findByValue(memberId + "-" + deviceUUID);
        if (device == null) {
            throw new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] ] not found refresh token");
        }
        refreshRepository.delete(memberId + "-" + deviceUUID);
        refreshRepository.delete(deviceUUID);
        refreshRepository.removeFromHash(memberId, deviceUUID);

//        String memberId = refreshRepository.findByValue(refreshToken);
//        if (memberId == null) {
//            throw new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] ] not found refresh token");
//        }
//
//        refreshRepository.delete(refreshToken);

        /**
         * 1안 ->  {memberId : {uuid : refresh, . . .}}
         * 2안 -> {memberId + uuid : refresh}
         */
        // 만약 uuid : refresh 가 존재한다면 uuid : refresh 삭제

    }

    private void checkAuthorized(String email, String principalEmail) {
        if (!email.equals(principalEmail)) {
            throw new UnauthorizedException(AuthErrorCode.UNAUTHORIZED_ERROR);
        }
    }
}
