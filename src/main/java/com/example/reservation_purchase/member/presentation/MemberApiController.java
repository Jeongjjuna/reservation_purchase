package com.example.reservation_purchase.member.presentation;

import com.example.reservation_purchase.auth.domain.UserDetailsImpl;
import com.example.reservation_purchase.member.application.MemberJoinService;
import com.example.reservation_purchase.member.application.ProfileService;
import com.example.reservation_purchase.member.domain.MemberCreate;
import com.example.reservation_purchase.member.presentation.response.MemberJoinResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/members")
public class MemberApiController {

    private final MemberJoinService memberJoinService;
    private final ProfileService profileService;

    public MemberApiController(final MemberJoinService memberJoinService, final ProfileService profileService) {
        this.memberJoinService = memberJoinService;
        this.profileService = profileService;
    }

    @PostMapping
    public ResponseEntity<MemberJoinResponse> join(@RequestBody final MemberCreate memberCreate) {
        return ResponseEntity.ok(memberJoinService.join(memberCreate));
    }

    @PostMapping("/{id}/profile")
    public ResponseEntity<String> uploadProfile(@PathVariable("id") final Long memberId,
                                                @RequestParam("file") MultipartFile multipartFile,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(profileService.upload(memberId, userDetails.getId(), multipartFile));
    }
}
