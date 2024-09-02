package com.suyh.security.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.RequestDispatcher;
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

        // 使用 RequestDispatcher 对象进行请求转发可以实现服务器端的内部跳转，而不会改变客户端的 URL。
        // 转发后，服务器端会处理新的请求，并将结果直接返回给客户端，客户端不会感知到转发的过程，因此 URL 也不会改变。
        // TODO: suyh - 这个还需要实现一个controller 接口
        String successForwardUrl = "/logout/after/successful";
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(successForwardUrl);
        requestDispatcher.forward(request, response);
    }
}
