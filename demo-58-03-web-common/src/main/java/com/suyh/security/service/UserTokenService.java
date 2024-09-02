package com.suyh.security.service;

import com.suyh.constant.ErrorCodeConstants;
import com.suyh.mvc.exception.ExceptionUtil;
import com.suyh.mvc.login.LoginUser;
import com.suyh.security.UserToken;
import com.suyh.util.TokenUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

/**
 * @author suyh
 * @since 2024-08-31
 */
@Slf4j
@Service
public class UserTokenService implements UserToken {

    /**
     * token 校验失败，则返回null，不要抛出异常。
     */
    @Override
    @Nullable
    public LoginUser loginUserDetail(String token) {
        try {
            // 解析token 得到用户
            Claims claims = TokenUtils.parseToken(token);
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
            return new LoginUser(id, username);
        } catch (Exception exception) {
            log.error("token authentication failed", exception);
            return null;
        }
    }
}
