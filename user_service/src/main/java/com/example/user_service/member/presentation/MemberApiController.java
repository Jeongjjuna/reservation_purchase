package com.example.user_service.member.presentation;

import com.example.user_service.auth.security.UserDetailsImpl;
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
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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
import java.net.URI;

@RestController
@RequestMapping("/v1/members")
public class MemberApiController {

    private final MemberReadService memberReadService;
    private final MemberJoinService memberJoinService;
    private final MemberUpdateService memberUpdateService;
    private final ProfileService profileService;

    public MemberApiController(
            final MemberReadService memberReadService,
            final MemberJoinService memberJoinService,
            final MemberUpdateService memberUpdateService,
            final ProfileService profileService
    ) {
        this.memberReadService = memberReadService;
        this.memberJoinService = memberJoinService;
        this.memberUpdateService = memberUpdateService;
        this.profileService = profileService;
    }

    /**
     * 회원가입
     */
    @PostMapping
    public ResponseEntity<MemberJoinResponse> signup(
            @RequestBody final MemberCreate memberCreate
    ) {
        MemberJoinResponse response = memberJoinService.signup(memberCreate);
        return ResponseEntity.created(URI.create("/v1/members/" + response.getId())).body(response);
    }

    /**
     * 프로필 이미지 업로드
     * (기존 이미지 있을 시 대체)
     */
    @PostMapping("/{id}/profile")
    public ResponseEntity<String> uploadProfile(@PathVariable("id") final Long memberId,
                                                @RequestParam("file") MultipartFile multipartFile,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        String url = profileService.upload(memberId, userDetails.getId(), multipartFile);
        return ResponseEntity.created(URI.create("/v1/members/" + memberId)).body(url);
    }

    /**
     * 이름, 인사말 업데이트
     */
    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(
            @RequestBody final MemberUpdate memberUpdate,
            @PathVariable("id") final Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        memberUpdateService.update(memberUpdate, id, userDetails.getId());
        return ResponseEntity.ok().build();
    }

    /**
     * 비밀번호 업데이트
     */
    @PatchMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword(
            @RequestBody final PasswordUpdate passwordUpdate,
            @PathVariable("id") final Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        memberUpdateService.updatePassword(passwordUpdate, id, userDetails.getId());
        return ResponseEntity.ok().build();
    }

    /**
     * 회원 정보 단건 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse> read(
            @PathVariable("id") final Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Member member = memberReadService.read(id, userDetails.getId());
        return ResponseEntity.ok(MemberResponse.from(member));
    }

    /**
     * 내가 팔로우한 회원들 조회
     */
    @GetMapping("/my-followings")
    public ResponseEntity<Page<MemberResponse>> myFollowing(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Page<MemberResponse> myFollows = memberReadService.readMyFollowing(userDetails.getId());
        return ResponseEntity.ok(myFollows);
    }

    /**
     * 나를 팔로우한 회원들 조회
     */
    @GetMapping("/my-followers")
    public ResponseEntity<Page<MemberResponse>> myFollowers(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Page<MemberResponse> myFollows = memberReadService.readMyFollowers(userDetails.getId());
        return ResponseEntity.ok(myFollows);
    }
}
