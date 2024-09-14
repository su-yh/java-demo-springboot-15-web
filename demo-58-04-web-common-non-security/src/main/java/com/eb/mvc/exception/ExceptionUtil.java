package com.eb.mvc.exception;

public class ExceptionUtil {

    public static BaseException business(int code, Object... params) {
        return new BaseException(ExceptionCategory.BUSINESS, code, params);
    }

    /**
     * 对于当前系统来说，所有的异常都返回200，所以这里的方法没有意义了。
     */
    @Deprecated
    public static BaseException system(int code, Object... params) {
        return new BaseException(ExceptionCategory.SYSTEM, code, params);
    }
}
