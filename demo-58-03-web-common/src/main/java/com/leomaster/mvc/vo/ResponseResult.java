package com.leomaster.mvc.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class ResponseResult<T> {
    public static final Integer SUCCESS_CODE = 0;
    public static final String SUCCESS_MSG = "SUCCESS";

    @Schema(description = "业务上的错误码，0 表示成功，其他表示失败。只有在status 的值为2xx 的时候该值才有效。")
    private final Integer code;

    private final String message;

    @Schema(description = "成功时的响应数据，status 的值为2xx，同时errorCode 的值为0 时，该值才有效，否则没有意义。")
    private final T data;

    private ResponseResult(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ResponseResult<T> ofSuccess(T data) {
        return new ResponseResult<>(SUCCESS_CODE, SUCCESS_MSG, data);
    }

    public static <T> ResponseResult<T> ofSuccess() {
        return new ResponseResult<>(SUCCESS_CODE, SUCCESS_MSG, null);
    }

    public static <T> ResponseResult<T> ofFail(int code, String message) {
        return new ResponseResult<>(code, message, null);
    }
}
