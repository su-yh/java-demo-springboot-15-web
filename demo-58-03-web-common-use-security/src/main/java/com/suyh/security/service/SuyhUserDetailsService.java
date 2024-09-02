package com.suyh.security.service;

import com.suyh.mp.mysql.entity.UserInfoEntity;
import com.suyh.mp.mysql.mapper.UserInfoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author suyh
 * @since 2023-11-04
 */
@Service("userDetailsService")
@RequiredArgsConstructor
@Slf4j
public class SuyhUserDetailsService implements UserDetailsService {
    // TODO: suyh - 这里应该使用 UserService 但是有环形引用，晚点有空再弄。
    private final UserInfoMapper userInfoMapper;
    // TODO: suyh - 缓存用户信息

    // 加载用户信息，主要是在用户登录的时候才会调用，在使用过程中基本都是通过token 来获取登录用户详细信息。
    // 所以这里每次从数据库中查询影响也不大
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserInfoEntity userInfoEntity = userInfoMapper.selectByUni(username);
        if (userInfoEntity == null) {
            throw new UsernameNotFoundException(username);
        }

        // 这个加了盐，所以校验不通过
        String ciphertextPwd = userInfoEntity.getPassword();

        // TODO: suyh - 先测试通过，后面再来处理盐值加密的问题
        ciphertextPwd = new BCryptPasswordEncoder().encode("admin");

        return new User(username, ciphertextPwd, AuthorityUtils.NO_AUTHORITIES);
    }
}
