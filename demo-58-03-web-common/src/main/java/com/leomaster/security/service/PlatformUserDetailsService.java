package com.leomaster.security.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.leomaster.business.vo.UserInfo;
import com.leomaster.config.base.properties.BaseProperties;
import com.leomaster.security.UserToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author suyh
 * @since 2023-11-04
 */
@Service("userDetailsService")
@RequiredArgsConstructor
@Slf4j
public class PlatformUserDetailsService implements UserDetailsService, LogoutSuccessHandler, UserToken {
    private static final String secret = "108badb496bd4a529527b2ee9c82d539";

    private final BaseProperties baseProperties;

    // key: username
    private final Cache<String, UserInfo> cacheUserInfo = Caffeine.newBuilder()
            .expireAfterWrite(8, TimeUnit.HOURS).initialCapacity(4).build();

    private final Map<String, String> userMap = new HashMap<>();

    @PostConstruct
    public void init() {
        List<UserInfo> users = baseProperties.getUsers();
        for (UserInfo user : users) {
            String password = userMap.get(user.getUsername());
            if (password != null) {
                log.error("用户配置重复了，username: {}", user.getUsername());
                throw new RuntimeException("用户配置重复了，username: " + user.getUsername());
            }

            userMap.put(user.getUsername(), user.getPassword());
        }
    }

    // 加载用户信息，主要是在用户登录的时候才会调用，在使用过程中基本都是通过token 来获取登录用户详细信息。
    // 所以这里每次从数据库中查询影响也不大
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 这个用户所拥有哪些权限
        // admins 是权限，ROLE_sale  是角色名称，ROLE_ 是源代码中要求的前缀
//        List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("admins,ROLE_sale");

        String password = userMap.get(username);
        if (password == null) {
            throw new UsernameNotFoundException(username);
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String ciphertextPassword = bCryptPasswordEncoder.encode(password);

        return new User(username, ciphertextPassword, new ArrayList<>());
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // TODO: suyh - 用户登出，需要清理相关缓存以及token 失效
    }

    @Override
    public User loginUserDetail(String token) {
        // 解析token 得到用户
        Claims claims = parseToken(token);
        String username = claims.getSubject();

        String password = userMap.get(username);
        return new User(username, password, AuthorityUtils.NO_AUTHORITIES);
    }

    public static String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setSubject(username)
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
