package com.leomaster.security.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author suyh
 * @since 2024-08-31
 */
@Service
public class SuyhLogoutService implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {
        // TODO: suyh - 用户登出，需要清理相关缓存以及token 失效
    }
}
