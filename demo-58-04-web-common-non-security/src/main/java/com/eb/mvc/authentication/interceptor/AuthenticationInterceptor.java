package com.eb.mvc.authentication.interceptor;

import com.eb.constant.ErrorCodeConstants;
import com.eb.mvc.authentication.LoginUser;
import com.eb.mvc.authentication.annotation.Permit;
import com.eb.mvc.exception.ExceptionUtil;
import com.eb.rouyi.service.SysPermissionService;
import com.eb.system.service.UserService;
import com.eb.util.TokenUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    private final UserService userService;
    private final SysPermissionService permissionService;

    /**
     * 对于非业务API 接口忽略认证的API 配置
     * 如果是业务相关的API 接口忽略认证使用注解{@link Permit}
     */
    private final String[] ignoreAuthPathPatterns = {
            // spring mvc 基础错误重定向API 接口
            "/error",
            "/**/*.js", "/**/*.css",
            // knife4j
            "/doc.html", "/v3/api-docs/**"};

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

        authentication(request, handlerMethod);

        return true;
    }

    @Override
    public void postHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler,
            ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);

        request.removeAttribute(LoginUser.LOGIN_USER_ATTRIBUTE_KEY);
    }

    // 认证
    private void authentication(@NonNull HttpServletRequest request, @NonNull HandlerMethod handlerMethod) {
        String userToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        LoginUser loginUser = parseLoginUser(userToken);

        // 正常登录
        if (loginUser != null) {
            request.setAttribute(LoginUser.LOGIN_USER_ATTRIBUTE_KEY, loginUser);
            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(loginUser, null, null);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            return;
        }

        // 未登录的情况

        // 匹配无需认证路径
        String servletPath = request.getServletPath();
        for (String pattern : ignoreAuthPathPatterns) {
            if (antPathMatcher.match(pattern, servletPath)) {
                return;
            }
        }

        // 匹配无需认证注解
        Permit permit = handlerMethod.getMethodAnnotation(Permit.class);
        if (permit != null && !permit.required()) {
            return;
        }

        throw ExceptionUtil.business(ErrorCodeConstants.TOKEN_ERROR_OR_EXPIRE);
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
        String nickname = claims.get(TokenUtils.NICK_NAME_KEY, String.class);
        if (username == null || strId == null || nickname == null) {
            log.error("claims value null, username: {}, id: {}, nickname: {}", username, strId, nickname);
            throw ExceptionUtil.business(ErrorCodeConstants.SERVICE_ERROR);
        }

        if (userService == null) {
            log.error("{} bean is null", UserService.class.getSimpleName());
            throw ExceptionUtil.business(ErrorCodeConstants.SERVICE_ERROR);
        }

        long id = Long.parseLong(strId);
        return new LoginUser(userService, permissionService, id, username, nickname);
    }
}
