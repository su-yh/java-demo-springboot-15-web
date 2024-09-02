package com.suyh.security;

import com.suyh.mvc.login.LoginUser;

/**
 * @author suyh
 * @since 2024-03-08
 */
public interface UserToken {
    LoginUser loginUserDetail(String token);
}
