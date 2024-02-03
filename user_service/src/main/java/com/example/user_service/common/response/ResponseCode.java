package com.example.user_service.common.response;

import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS("success", "0000"),
    FAILURE("failure", "9000");

    private final String desc;
    private final String code;

    ResponseCode(final String desc, final String code) {
        this.desc = desc;
        this.code = code;
    }
}
