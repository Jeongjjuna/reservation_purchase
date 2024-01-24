package com.example.reservation_purchase.member.presentation;

import com.example.reservation_purchase.member.application.MemberJoinService;
import com.example.reservation_purchase.member.domain.MemberCreate;
import com.example.reservation_purchase.member.presentation.response.MemberJoinResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberApiController {

    private final MemberJoinService memberJoinService;

    public MemberApiController(final MemberJoinService memberJoinService) {
        this.memberJoinService = memberJoinService;
    }

    @PostMapping
    public ResponseEntity<MemberJoinResponse> join(@RequestBody final MemberCreate memberCreate) {
        return ResponseEntity.ok(memberJoinService.join(memberCreate));
    }
}
