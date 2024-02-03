package com.example.user_service.member.client;

import lombok.Getter;
import java.util.List;

@Getter
public class MemberList {
    private String desc;
    private String code;
    private List<Long> data;

    public MemberList(final String desc, final String code, final List<Long> data) {
        this.desc = desc;
        this.code = code;
        this.data = data;
    }
}
