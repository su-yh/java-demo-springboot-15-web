package com.suyh.business.service;

import com.suyh.business.dto.UserCreateDto;
import com.suyh.config.base.properties.BaseProperties;
import com.suyh.constant.ErrorCodeConstants;
import com.suyh.mp.mysql.entity.UserInfoEntity;
import com.suyh.mp.mysql.mapper.UserInfoMapper;
import com.suyh.mvc.exception.ExceptionUtil;
import com.suyh.util.JsonUtils;
import com.suyh.util.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
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
    private final BaseProperties baseProperties;
    private final PasswordEncoder passwordEncoder;

    private final UserInfoMapper userInfoMapper;

    @PostConstruct
    public void init() {
        UserInfoEntity entity = userInfoMapper.selectById(1);
        log.info("suyh - entity: {}", JsonUtils.serializable(entity));
    }

    public String login(@NonNull String username, @NonNull String password) {
        UserInfoEntity historyEntity = userInfoMapper.selectByUni(username);
        if (historyEntity == null) {
            throw ExceptionUtil.business(ErrorCodeConstants.USER_BAD_CREDENTIALS);
        }

        boolean matchFlag = passwordEncoder.matches(password + historyEntity.getSalt(), historyEntity.getPassword());
        if (!matchFlag) {
            throw ExceptionUtil.business(ErrorCodeConstants.USER_BAD_CREDENTIALS);
        }

        Map<String, Object> claims = new HashMap<>();
        return TokenUtils.createToken(claims, historyEntity.getId(), username, baseProperties.getTokenSeconds());
    }

    @Transactional
    public Long createUser(UserCreateDto dto) {
        UserInfoEntity historyEntity = userInfoMapper.selectByUni(dto.getUsername());
        if (historyEntity != null) {
            throw ExceptionUtil.business(ErrorCodeConstants.USER_EXISTS, dto.getUsername());
        }

        String username = dto.getUsername();
        String password = dto.getPassword();
        String salt = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
        String ciphertextPwd = passwordEncoder.encode(password + salt);

        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setUsername(username)
                .setPassword(ciphertextPwd)
                .setNickname(dto.getNickname())
                .setSalt(salt);

        userInfoMapper.insert(userInfoEntity);

        return userInfoEntity.getId();
    }
}
