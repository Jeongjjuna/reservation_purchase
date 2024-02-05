package com.example.user_service.member.presentation.response;

import com.example.user_service.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class MemberResponse {

    private Long id;
    private String email;
    private String name;
    private String greetings;
    protected LocalDateTime createdAt;

    @Builder
    public MemberResponse(
            final Long id,
            final String email,
            final String name,
            final String greetings,
            final LocalDateTime createdAt
    ) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.greetings = greetings;
        this.createdAt = createdAt;
    }

    public static MemberResponse from(final Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .greetings(member.getGreetings())
                .createdAt(member.getCreatedAt())
                .build();
    }
}
