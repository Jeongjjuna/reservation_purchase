package com.example.reservation_purchase.auth.application;

import com.example.reservation_purchase.auth.application.port.RefreshRepository;
import com.example.reservation_purchase.auth.domain.RefreshTokenInfo;
import com.example.reservation_purchase.auth.domain.TokenType;
import com.example.reservation_purchase.auth.presentation.response.RefreshResponse;
import com.example.reservation_purchase.exception.GlobalException;
import com.example.reservation_purchase.member.application.port.MemberRepository;
import com.example.reservation_purchase.member.domain.Member;
import com.example.reservation_purchase.member.exception.MemberErrorCode;
import com.example.reservation_purchase.member.exception.MemberException.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final RefreshRepository refreshRepository;

    public RefreshTokenService(final JwtTokenProvider jwtTokenProvider, final MemberRepository memberRepository, final RefreshRepository refreshRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.memberRepository = memberRepository;
        this.refreshRepository = refreshRepository;
    }

    public RefreshResponse refresh(final RefreshTokenInfo refreshTokenInfo, final String deviceUUID) {
        String refreshToken = refreshTokenInfo.getRefreshToken();

        // 1. redis 저장소에 해당 기기에 refresh 토큰이 존재하는지 확인한다.
        String findRefresh = refreshRepository.findByValue(deviceUUID);
        if (findRefresh == null) {
            throw new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] ] not found refresh token");
        }

        // 2. 해당 기기의 토큰과, 재발급 요청한 토큰이 같은지 확인한다.
        if (!findRefresh.equals(refreshTokenInfo.getRefreshToken())) {
            throw new GlobalException(HttpStatus.NOT_FOUND, "[ERROR] ] not correct refresh token");
        }

        // 3. refresh로부터 member정보를 꺼내 DB에서 가져온다.
        String email = jwtTokenProvider.getEmail(refreshToken, TokenType.REFRESH);
        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new MemberNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND));

        // 만료기간이 안끝난 refresh토큰만 입력받는다고 가정한다.
        // (기한이 지나면 브라우저 상에서 삭제되어 보낼 수 없음)
//        if (jwtTokenProvider.isExpired(refreshToken)) {
//            throw new IllegalArgumentException("재발급 불가. 다시 로그인 하세요.");
//        }

        // 4. AccessToken을 발급하여 기존 RefreshToken과 함께 응답한다.
        String accessToken = jwtTokenProvider.generate(email, member.getName(), TokenType.ACCESS);

        // TODO : 리프레쉬 기간이 얼마 안남으면 그냥 리프레쉬도 새로 발급해준다.(보류)
        return RefreshResponse.from(accessToken, refreshToken);
    }
}
