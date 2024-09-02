package com.suyh5804.business.service;

import com.suyh5804.business.dto.UserCreateDto;
import com.suyh5804.config.base.properties.BaseProperties;
import com.suyh5804.constant.ErrorCodeConstants;
import com.suyh5804.mp.mysql.entity.UserEntity;
import com.suyh5804.mp.mysql.mapper.UserMapper;
import com.suyh5804.mvc.exception.ExceptionUtil;
import com.suyh5804.util.JsonUtils;
import com.suyh5804.util.TokenUtils;
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

    private final UserMapper userMapper;

    // TODO: suyh - 测试
    @PostConstruct
    public void init() {
        UserEntity entity = userMapper.selectById(1);
        log.info("suyh - entity: {}", JsonUtils.serializable(entity));
    }

    public String login(@NonNull String username, @NonNull String password) {
        UserEntity historyEntity = userMapper.selectByUni(username);
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
        UserEntity historyEntity = userMapper.selectByUni(dto.getUsername());
        if (historyEntity != null) {
            throw ExceptionUtil.business(ErrorCodeConstants.USER_EXISTS, dto.getUsername());
        }

        String username = dto.getUsername();
        String password = dto.getPassword();
        String salt = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
        String ciphertextPwd = passwordEncoder.encode(password + salt);

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username)
                .setPassword(ciphertextPwd)
                .setNickname(dto.getNickname())
                .setSalt(salt);

        userMapper.insert(userEntity);

        return userEntity.getId();
    }
}
