package com.example.user_service.common.response;

import lombok.Getter;

@Getter
public class Response<T> {
    private String desc;
    private String code;
    private T data;

    public Response(final ResponseCode responseCode, final T data) {
        this.desc = responseCode.getDesc();
        this.code = responseCode.getCode();
        this.data = data;
    }

    public static <T> Response<T> success(final ResponseCode responseCode, final T data) {
        return new Response<>(responseCode, data);
    }

    public static <T> Response<T> success(final T data) {
        return new Response<>(ResponseCode.SUCCESS, data);
    }

    public static <T> Response<T> success() {
        return new Response<>(ResponseCode.SUCCESS, null);
    }

    public static <T> Response<T> error(final ResponseCode responseCode, final T  data) {
        return new Response<>(responseCode, data);
    }

    public static <T> Response<T> error(final T  data) {
        return new Response<>(ResponseCode.FAILURE, data);
    }
}
