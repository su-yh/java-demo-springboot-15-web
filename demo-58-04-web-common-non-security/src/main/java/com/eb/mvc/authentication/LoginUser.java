package com.eb.mvc.authentication;

import com.eb.constant.ErrorCodeConstants;
import com.eb.mvc.exception.ExceptionUtil;
import com.eb.rouyi.entity.SysUser;
import com.eb.rouyi.service.SysPermissionService;
import com.eb.system.service.UserService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public class LoginUser {
    public static final String LOGIN_USER_ATTRIBUTE_KEY = "loginUser";

    public LoginUser(UserService userService, SysPermissionService permissionService, Long id, String username, String nickname) {
        if (userService == null || id == null || username == null || nickname == null) {
            log.error("userService or id or username is null!, id: {}, username: {}, nickname: {}",
                    id, username, nickname);
            throw ExceptionUtil.business(ErrorCodeConstants.SERVICE_ERROR);
        }

        this.userService = userService;
        this.permissionService = permissionService;
        this.id = id;
        this.username = username;
        this.nickname = nickname;
    }

    private final UserService userService;
    private final SysPermissionService permissionService;

    @Getter
    private final Long id;
    @Getter
    private final String username;
    @Getter
    private final String nickname;

    /**
     * 权限列表
     */
    private Set<String> permissions;

    private volatile SysUser user;

    public SysUser getUser() {
        if (user == null) {
            synchronized (this) {
                if (user == null) {
                    user = userService.obtainUserById(id);
                }
            }
        }

        return user;
    }

    public Set<String> getPermissions() {
        if (permissions == null) {
            SysUser user = getUser();
            synchronized (this) {
                if (permissions == null) {
                    permissions = permissionService.getMenuPermission(user);
                }
            }
        }
        return permissions;
    }
}
