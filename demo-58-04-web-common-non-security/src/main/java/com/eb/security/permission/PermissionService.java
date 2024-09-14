package com.eb.security.permission;

import com.eb.mvc.authentication.LoginUser;
import com.eb.rouyi.util.RuoyiConstants;
import com.eb.rouyi.util.RuoyiStringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;

import java.util.Set;

/**
 * RuoYi首创 自定义权限实现，ss取自SpringSecurity首字母
 * 
 * @author ruoyi
 */
public class PermissionService
{
    /**
     * 通过静态方法取当前登录用户，在确定没有线程安全问题的情况下才可以使用。
     * 一般在controller 层，以及之前才可以这样使用。
     * 到达业务层之后，都不允许直接通过静态方法获取当前登录用户。
     */
    private static LoginUser getLoginUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof LoginUser) {
            return (LoginUser) principal;
        }

        return null;
    }
    /**
     * 验证用户是否具备某权限
     * 
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    public boolean hasPermi(String permission)
    {
        LoginUser loginUser = PermissionService.getLoginUser();
        if (RuoyiStringUtils.isEmpty(permission))
        {
            return false;
        }
        if (RuoyiStringUtils.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getPermissions()))
        {
            return false;
        }
        return hasPermissions(loginUser.getPermissions(), permission);
    }

    /**
     * 验证用户是否具有以下任意一个权限
     *
     * @param permissions 以 PERMISSION_DELIMETER 为分隔符的权限列表
     * @return 用户是否具有以下任意一个权限
     */
    public boolean hasAnyPermi(String permissions)
    {
        LoginUser loginUser = PermissionService.getLoginUser();
        if (RuoyiStringUtils.isEmpty(permissions))
        {
            return false;
        }
        if (RuoyiStringUtils.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getPermissions()))
        {
            return false;
        }
        Set<String> authorities = loginUser.getPermissions();
        for (String permission : permissions.split(RuoyiConstants.PERMISSION_DELIMETER))
        {
            if (permission != null && hasPermissions(authorities, permission))
            {
                return true;
            }
        }
        return false;
    }

//    /**
//     * 判断用户是否拥有某个角色
//     *
//     * @param role 角色字符串
//     * @return 用户是否具备某角色
//     */
//    public boolean hasRole(String role)
//    {
//        if (RuoyiStringUtils.isEmpty(role))
//        {
//            return false;
//        }
//        LoginUser loginUser = SecurityUtils.getLoginUser();
//        if (RuoyiStringUtils.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getUser().getRoles()))
//        {
//            return false;
//        }
//        for (SysRole sysRole : loginUser.getUser().getRoles())
//        {
//            String roleKey = sysRole.getRoleKey();
//            if (Constants.SUPER_ADMIN.equals(roleKey) || roleKey.equals(RuoyiStringUtils.trim(role)))
//            {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 验证用户是否不具备某角色，与 isRole逻辑相反。
//     *
//     * @param role 角色名称
//     * @return 用户是否不具备某角色
//     */
//    public boolean lacksRole(String role)
//    {
//        return hasRole(role) != true;
//    }
//
//    /**
//     * 验证用户是否具有以下任意一个角色
//     *
//     * @param roles 以 ROLE_NAMES_DELIMETER 为分隔符的角色列表
//     * @return 用户是否具有以下任意一个角色
//     */
//    public boolean hasAnyRoles(String roles)
//    {
//        if (RuoyiStringUtils.isEmpty(roles))
//        {
//            return false;
//        }
//        LoginUser loginUser = SecurityUtils.getLoginUser();
//        if (RuoyiStringUtils.isNull(loginUser) || CollectionUtils.isEmpty(loginUser.getUser().getRoles()))
//        {
//            return false;
//        }
//        for (String role : roles.split(Constants.ROLE_DELIMETER))
//        {
//            if (hasRole(role))
//            {
//                return true;
//            }
//        }
//        return false;
//    }

    /**
     * 判断是否包含权限
     *
     * @param permissions 权限列表
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    private boolean hasPermissions(Set<String> permissions, String permission)
    {
        return permissions.contains(RuoyiConstants.ALL_PERMISSION) || permissions.contains(RuoyiStringUtils.trim(permission));
    }
}
