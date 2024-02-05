package com.example.user_service.member.presentation;

import com.example.user_service.auth.security.UserDetailsImpl;
import com.example.user_service.common.response.Response;
import com.example.user_service.member.application.MemberJoinService;
import com.example.user_service.member.application.MemberReadService;
import com.example.user_service.member.application.MemberUpdateService;
import com.example.user_service.member.application.ProfileService;
import com.example.user_service.member.domain.Member;
import com.example.user_service.member.domain.MemberCreate;
import com.example.user_service.member.domain.MemberUpdate;
import com.example.user_service.member.domain.PasswordUpdate;
import com.example.user_service.member.presentation.response.MemberJoinResponse;
import com.example.user_service.member.presentation.response.MemberResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/members")
public class MemberApiController {

    private final MemberReadService memberReadService;
    private final MemberJoinService memberJoinService;
    private final MemberUpdateService memberUpdateService;
    private final ProfileService profileService;

    /**
     * 회원가입
     */
    @PostMapping
    public Response<MemberJoinResponse> signup(
            @RequestBody final MemberCreate memberCreate
    ) {
        final MemberJoinResponse response = memberJoinService.signup(memberCreate);
        return Response.success(response);
    }

    /**
     * 프로필 이미지 업로드
     * (기존 이미지 있을 시 대체)
     */
    @PostMapping("/{id}/profile")
    public Response<String> uploadProfile(
            @PathVariable("id") final Long memberId,
            @RequestParam("file") final MultipartFile multipartFile,
            @AuthenticationPrincipal final UserDetailsImpl userDetails
    ) {
        final String url = profileService.upload(memberId, userDetails.getId(), multipartFile);
        return Response.success(url);
    }

    /**
     * 이름, 인사말 업데이트
     */
    @PatchMapping("/{id}")
    public Response<Void> update(
            @RequestBody final MemberUpdate memberUpdate,
            @PathVariable("id") final Long id,
            @AuthenticationPrincipal final UserDetailsImpl userDetails
    ) {
        memberUpdateService.update(memberUpdate, id, userDetails.getId());
        return Response.success();
    }

    /**
     * 비밀번호 업데이트
     */
    @PatchMapping("/{id}/password")
    public Response<Void> updatePassword(
            @RequestBody final PasswordUpdate passwordUpdate,
            @PathVariable("id") final Long id,
            @AuthenticationPrincipal final UserDetailsImpl userDetails
    ) {
        memberUpdateService.updatePassword(passwordUpdate, id, userDetails.getId());
        return Response.success();
    }

    /**
     * 회원 정보 단건 조회
     */
    @GetMapping("/{id}")
    public Response<MemberResponse> read(
            @PathVariable("id") final Long id,
            @AuthenticationPrincipal final UserDetailsImpl userDetails
    ) {
        final Member member = memberReadService.read(id, userDetails.getId());
        return Response.success(MemberResponse.from(member));
    }

    /**
     * 내가 팔로우한 회원들 조회
     */
    @GetMapping("/my-followings")
    public Response<Page<MemberResponse>> myFollowing(
            @RequestParam(name = "member") final Long principalId
    ) {
        final Page<MemberResponse> myFollows = memberReadService.readMyFollowing(principalId);
        return Response.success(myFollows);
    }

    /**
     * 나를 팔로우한 회원들 조회
     */
    @GetMapping("/my-followers")
    public Response<Page<MemberResponse>> myFollowers(
            @RequestParam(name = "member") final Long principalId
    ) {
        final Page<MemberResponse> myFollows = memberReadService.readMyFollowers(principalId);
        return Response.success(myFollows);
    }
}
