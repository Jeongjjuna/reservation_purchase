package com.example.newsfeed_service.newsfeed.client;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
public class FollowingIdList {
    private String desc;
    private String code;
    private List<Long> data;

    @Builder
    public FollowingIdList(final String desc, final String code, final List<Long> data) {
        this.desc = desc;
        this.code = code;
        this.data = data;
    }
}
