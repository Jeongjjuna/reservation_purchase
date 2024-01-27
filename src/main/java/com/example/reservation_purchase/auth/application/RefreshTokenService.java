package com.example.reservation_purchase.auth.application;

import com.example.reservation_purchase.auth.application.port.RefreshRepository;
import com.example.reservation_purchase.auth.domain.RefreshTokenInfo;
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

    public RefreshResponse refresh(final RefreshTokenInfo refreshTokenInfo) {
        String refreshToken = refreshTokenInfo.getRefreshToken();

        // 1. refresh저장소에 해당 refresh토큰이 존재하는지 확인한다.
        refreshRepository.findByValue(refreshTokenInfo.getRefreshToken()).orElseThrow(() ->
                new GlobalException(HttpStatus.NOT_FOUND, "리프레쉬 토큰이 존재하지 않습니다."));

        // 2. refresh로부터 member정보를 꺼내 DB에서 가져온다.
        String email = jwtTokenProvider.getEmail(refreshToken);
        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new MemberNotFoundException(MemberErrorCode.MEMBER_NOT_FOUND));

        // 만료기간이 안끝난 refresh토큰만 입력받는다고 가정한다.
        // (기한이 지나면 브라우저 상에서 삭제되어 보낼 수 없음)
//        if (jwtTokenProvider.isExpired(refreshToken)) {
//            throw new IllegalArgumentException("재발급 불가. 다시 로그인 하세요.");
//        }

        // 3. AccessToken을 발급하여 기존 RefreshToken과 함께 응답한다.
        String accessToken = jwtTokenProvider.generateAccess(email, member.getName());

        // 리프레쉬 기간이 얼마 안남으면 그냥 리프레쉬도 새로 발급해준다.(보류)
        return RefreshResponse.from(accessToken, refreshToken);
    }
}
