package com.leomaster.security.filter;

import com.leomaster.security.UserToken;
import com.leomaster.security.vo.PlatformAuthenticationToken;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author suyh
 * @since 2024-03-08
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private final UserToken userToken;

    public JwtAuthenticationTokenFilter(UserToken userToken) {
        this.userToken = userToken;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(token)) {
            User user = userToken.loginUserDetail(token);
            PlatformAuthenticationToken authenticationToken = new PlatformAuthenticationToken(user);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // 只要往上下文中添加该实例对象就被认为是认证通过了。
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        chain.doFilter(request, response);
    }
}
