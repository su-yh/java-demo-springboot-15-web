package com.eb.system.service;

import com.eb.config.base.properties.BaseProperties;
import com.eb.constant.ErrorCodeConstants;
import com.eb.mvc.exception.ExceptionUtil;
import com.eb.rouyi.entity.SysUser;
import com.eb.rouyi.mapper.SysUserMapper;
import com.eb.util.TokenUtils;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author suyh
 * @since 2024-08-31
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final GoogleAuthenticator googleAuth = new GoogleAuthenticator();

    private final BaseProperties baseProperties;
    private final PasswordEncoder passwordEncoder;

    private final SysUserMapper userMapper;

    public String login(@NonNull String username, @NonNull String password, @NonNull Integer code) {
        SysUser historyEntity = userMapper.selectByUni(username);
        if (historyEntity == null) {
            throw ExceptionUtil.business(ErrorCodeConstants.USER_BAD_CREDENTIALS);
        }

        validUser2Fa(historyEntity.getTwoFactorAuthKey(), code);

        String sourcePassword = password + historyEntity.getSalt();
        boolean matchFlag = passwordEncoder.matches(sourcePassword, historyEntity.getPassword());
        if (!matchFlag) {
            throw ExceptionUtil.business(ErrorCodeConstants.USER_BAD_CREDENTIALS);
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put(TokenUtils.NICK_NAME_KEY, historyEntity.getNickname());
        return TokenUtils.createToken(claims, historyEntity.getId(), username, baseProperties.getTokenSeconds());
    }

    @Transactional
    public int createUser(SysUser sysUser) {
        SysUser historyEntity = userMapper.selectByUni(sysUser.getUsername());
        if (historyEntity != null) {
            throw ExceptionUtil.business(ErrorCodeConstants.USER_EXISTS, sysUser.getUsername());
        }

        String password = sysUser.getPassword();
        String salt = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);

        String sourcePassword = password + salt;
        String ciphertextPwd = passwordEncoder.encode(sourcePassword);

        String twoFactorAuthkey = googleAuth.createCredentials().getKey();

        sysUser.setPassword(ciphertextPwd);
        sysUser.setSalt(salt);
        sysUser.setTwoFactorAuthKey(twoFactorAuthkey);

        return userMapper.insertUser(sysUser);
    }

    private void validUser2Fa(String twoFactorAuthKey, Integer codeFa) {
        if (!baseProperties.getCaptcha().isTwoFactorAuthEnabled()) {
            return;
        }

        boolean isCodeValid = googleAuth.authorize(twoFactorAuthKey, codeFa);
        if (!isCodeValid) {
            throw ExceptionUtil.business(ErrorCodeConstants.USER_LOGIN_CAPTCHA_ERROR);
        }
    }

    public SysUser obtainUserById(Long userId) {
        if (userId == null) {
            return null;
        }

        return userMapper.selectUserById(userId);
    }

    @Transactional
    public void updateUserPwd(@NonNull Long userId, @NonNull String password) {
        SysUser historyEntity = obtainUserById(userId);
        if (historyEntity == null) {
            throw ExceptionUtil.business(ErrorCodeConstants.USER_NOT_EXISTS);
        }

        String salt = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
        String ciphertextPwd = passwordEncoder.encode(password + salt);

        SysUser user = new SysUser();
        user.setUserId(userId);
        user.setPassword(ciphertextPwd);
        user.setSalt(salt);

        userMapper.updateUser(user);
    }

    @Transactional
    public String resetTwoFactorAuthKey(@NonNull Long id) {
        SysUser historyEntity = obtainUserById(id);
        if (historyEntity == null) {
            throw ExceptionUtil.business(ErrorCodeConstants.USER_NOT_EXISTS);
        }

        String twoFactorAuthkey = googleAuth.createCredentials().getKey();

        SysUser updateEntity = new SysUser();
        updateEntity.setId(id).setTwoFactorAuthKey(twoFactorAuthkey);

        userMapper.updateUser(updateEntity);

        return twoFactorAuthkey;
    }

    public SysUser queryEntityById(@NonNull Long id) {
        SysUser entity = userMapper.selectUserById(id);
        if (entity == null) {
            throw ExceptionUtil.business(ErrorCodeConstants.USER_NOT_EXISTS);
        }

        return entity;
    }

    @Transactional
    public void updatePwdByOldValue(
            @NonNull Long userId, @NonNull String oldPassword, @NonNull String newPassword) {
        SysUser historyEntity = obtainUserById(userId);
        if (historyEntity == null) {
            throw ExceptionUtil.business(ErrorCodeConstants.USER_NOT_EXISTS);
        }

        String sourcePassword = oldPassword + historyEntity.getSalt();
        boolean matchFlag = passwordEncoder.matches(sourcePassword, historyEntity.getPassword());
        if (!matchFlag) {
            throw ExceptionUtil.business(ErrorCodeConstants.USER_OLD_PASSWORD_NOT_MATCH);
        }

        updateUserPwd(userId, newPassword);
    }
}
