package com.leomaster.security.vo;

import com.leomaster.mvc.login.LoginUser;
import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * @author suyh
 * @since 2024-03-08
 */
public class SuyhAuthenticationToken extends AbstractAuthenticationToken {
    private final LoginUser loginUser;
    public SuyhAuthenticationToken(LoginUser loginUser) {
        // 权限一般不使用spring security 内置的。
        super(null);

        this.loginUser = loginUser;

        // 已经认证通过
        super.setAuthenticated(true); // must use super, as we override
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    // 这个一般是登录用户实体
    @Override
    public Object getPrincipal() {
        return loginUser;
    }
}
