package com.leomaster.mvc.exception;

import com.leomaster.constant.ErrorCode;
import org.springframework.lang.NonNull;

public class ExceptionUtil {

    public static BaseException business(@NonNull ErrorCode errorCode, Object... params) {
        return new BaseException(ExceptionCategory.BUSINESS, errorCode, params);
    }

    public static BaseException system(@NonNull ErrorCode errorCode, Object... params) {
        return new BaseException(ExceptionCategory.SYSTEM, errorCode, params);
    }
}
