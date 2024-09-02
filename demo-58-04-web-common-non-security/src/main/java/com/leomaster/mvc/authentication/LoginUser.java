package com.leomaster.mvc.authentication;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class LoginUser {
    public static final String LOGIN_USER_ATTRIBUTE_KEY = "loginUser";

    private final Long id;
    private final String username;
}
