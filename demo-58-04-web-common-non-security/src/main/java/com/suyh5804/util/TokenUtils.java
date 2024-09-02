package com.suyh5804.util;

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
    private static final String secret = "108badb496bd4a529527b2ee9c82d539";

    public static String createToken(Map<String, Object> claims, Long id, String username, Integer tokenSeconds) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + tokenSeconds * 1000);

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
            log.error("token parse failed.", exception);
            return null;
        }
    }
}
