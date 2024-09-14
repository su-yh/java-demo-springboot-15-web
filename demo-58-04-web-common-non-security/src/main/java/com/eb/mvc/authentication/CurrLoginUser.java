package com.eb.mvc.authentication;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于获取当前登陆人信息的注解,配合自定义的参数处理器使用
 *
 * @see CurrUserArgumentResolver
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrLoginUser {
    // FIXME: suyh - 当前来说不支持值为false
    boolean required() default true;    // 用户是否必须登录
}

