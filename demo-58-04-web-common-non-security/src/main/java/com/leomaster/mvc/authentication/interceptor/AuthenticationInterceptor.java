package com.leomaster.mvc.authentication.interceptor;

import com.leomaster.constant.ErrorCodeConstants;
import com.leomaster.mvc.authentication.LoginUser;
import com.leomaster.mvc.authentication.annotation.Permit;
import com.leomaster.mvc.exception.ExceptionUtil;
import com.leomaster.util.TokenUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * 对于非业务API 接口忽略认证的API 配置
     * 如果是业务相关的API 接口忽略认证使用注解{@link Permit}
     */
    private final String[] ignoreAuthPathPatterns = {
            // spring mvc 基础错误重定向API 接口
            "/error",
            "/**/*.js", "/**/*.css",
            // knife4j
            "/doc.html",  "/v3/api-docs/**"};

    // 这个方法是在访问接口之前执行的，我们只需要在这里写验证登陆状态的业务逻辑，就可以在用户调用指定接口之前验证登陆状态了
    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler) throws Exception {
        HandlerMethod handlerMethod = (handler instanceof HandlerMethod) ? (HandlerMethod) handler : null;
        if (handlerMethod == null) {
            return true;
        }

        String userToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        LoginUser loginUser = parseLoginUser(userToken);

        if (loginUser != null) {
            request.setAttribute(LoginUser.LOGIN_USER_ATTRIBUTE_KEY, loginUser);
            return true;
        }

        String servletPath = request.getServletPath();
        for (String pattern : ignoreAuthPathPatterns) {
            if (antPathMatcher.match(pattern, servletPath)) {
                return true;
            }
        }

        Permit permit = handlerMethod.getMethodAnnotation(Permit.class);
        if (permit == null || permit.required()) {
            throw ExceptionUtil.business(ErrorCodeConstants.TOKEN_ERROR_OR_EXPIRE);
        }

        return true;
    }

    @Nullable
    private LoginUser parseLoginUser(String userToken) {
        if (!StringUtils.hasText(userToken)) {
            return null;
        }

        Claims claims = TokenUtils.parseToken(userToken);
        if (claims == null) {
            return null;
        }

        String username = claims.getSubject();
        String strId = claims.getId();
        if (username == null || strId == null) {
            log.error("claims value null, username: {}, id: {}", username, strId);
            throw ExceptionUtil.business(ErrorCodeConstants.SERVICE_ERROR);
        }

        long id = Long.parseLong(strId);
        return new LoginUser(id, username);
    }
}
