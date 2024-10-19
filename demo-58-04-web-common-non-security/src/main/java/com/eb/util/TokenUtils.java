package com.eb.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;

import java.util.Date;
import java.util.Map;

/**
 * @author suyh
 * @since 2024-08-28
 */
@Slf4j
public final class TokenUtils {
    private static final String secret = "fd63a918dcaa44e68d78f0c806df9c41";

    public static final String NICK_NAME_KEY = "nickname";

    public static String createToken(Map<String, Object> claims, Long id, String username, Integer tokenSeconds) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + tokenSeconds * 1000L);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setId(id + "")
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    @Nullable
    public static Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception exception) {
            log.error("token parse failed. token: {}", token, exception);
            return null;
        }
    }
}
