package com.example.reservation_purchase.member.domain;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class Member {

    private Long id;
    private String email;
    private String password;
    private String name;
    private String greetings;
    private String profileUrl;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    @Builder
    public Member(final Long id, final String email, final String password, final String name, final String greetings,
                  final String profileUrl, final LocalDateTime createdAt, final LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.greetings = greetings;
        this.profileUrl = profileUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Member create(final MemberCreate memberCreate) {
        return Member.builder()
                .email(memberCreate.getEmail())
                .password(memberCreate.getPassword())
                .name(memberCreate.getName())
                .greetings(memberCreate.getGreetings())
                .build();
    }

    public void applyEncodedPassword(final String encodedPassword) {
        this.password = encodedPassword;
    }

    public void saveProfile(final String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public boolean isProfileUploaded() {
        return this.profileUrl != null;
    }
}
