package com.leomaster.mvc.login.interceptor;

import com.leomaster.constant.ErrorCodeConstants;
import com.leomaster.mvc.exception.ExceptionUtil;
import com.leomaster.mvc.login.LoginUser;
import com.leomaster.util.TokenUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Slf4j
public class LoginUserInterceptor implements HandlerInterceptor {
    // 这个方法是在访问接口之前执行的，我们只需要在这里写验证登陆状态的业务逻辑，就可以在用户调用指定接口之前验证登陆状态了
    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        // TODO: suyh - 测试security
        if (true) {
            return true;
        }

        if (!(handler instanceof HandlerMethod)) {
            // HandlerMethod handlerMethod = (HandlerMethod) handler;
            return true;
        }

        String userToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(userToken)) {
            throw ExceptionUtil.business(ErrorCodeConstants.USER_NOT_LOGIN);
        }

        Claims claims = TokenUtils.parseToken(userToken);
        if (claims == null) {
            throw ExceptionUtil.business(ErrorCodeConstants.TOKEN_ERROR_OR_EXPIRE);
        }

        String username = claims.getSubject();
        String strId = claims.getId();
        if (username == null || strId == null) {
            log.error("claims value null, username: {}, id: {}", username, strId);
            throw ExceptionUtil.business(ErrorCodeConstants.SERVICE_ERROR);
        }

        long id = Long.parseLong(strId);

        request.setAttribute(LoginUser.LOGIN_USER_ATTRIBUTE_KEY, new LoginUser(id, username));
        return true;
    }
}
