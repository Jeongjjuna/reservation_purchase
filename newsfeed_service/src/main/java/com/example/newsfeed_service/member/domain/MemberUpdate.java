package com.example.newsfeed_service.member.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberUpdate {

    private String name;
    private String greetings;

    @Builder
    public MemberUpdate(final String name, final String greetings) {
        this.name = name;
        this.greetings = greetings;
    }

}
