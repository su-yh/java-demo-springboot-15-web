package com.eb.rouyi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.eb.constant.enums.RuoyiEnableStatusEnums;
import com.eb.rouyi.excel.annotation.RuoyiExcel;
import com.eb.rouyi.excel.handler.ExcelEnumValueHandler;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户对象 sys_user
 * 
 * @author ruoyi
 */
public class SysUser implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String MESSAGE_PREFIX = "excel.title.SysUser";

    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String, Object> params;

    @TableId(type = IdType.AUTO)
    @Getter
    @Setter
    @RuoyiExcel(name = "id", nameCode = MESSAGE_PREFIX + ".id", cellType = RuoyiExcel.ColumnType.NUMERIC)
    private Long id;

    @Size(max = 64)
    @Getter
    @Setter
    @RuoyiExcel(name = "username", nameCode = MESSAGE_PREFIX + ".username", cellType = RuoyiExcel.ColumnType.STRING)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Getter
    @Setter
    @Size(max = 64)
    private String password;

    @Getter
    @Setter
    @Size(max = 64)
    @RuoyiExcel(name = "nickname", nameCode = MESSAGE_PREFIX + ".nickname", cellType = RuoyiExcel.ColumnType.STRING)
    private String nickname;

    @JsonIgnore
    @Getter
    @Setter
    private String salt;

    /**
     * 二次认证key
     */
    @Getter
    @Setter
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String twoFactorAuthKey;

    @Getter
    @Setter
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date created;

    @Getter
    @Setter
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Date updated;

//    /** 用户ID */
//    private Long userId;

    /** 部门ID */
    private Long deptId;

    /** 部门父ID */
    private Long parentId;

    /** 角色ID */
    private Long roleId;

    // suyh - 当前使用 username
//    /** 登录名称 */
//    private String loginName;

    // suyh - 当前使用 nick_name
//    /** 用户名称 */
//    private String userName;

    /** 用户类型 */
    @RuoyiExcel(name = "userType", nameCode = MESSAGE_PREFIX + ".userType", cellType = RuoyiExcel.ColumnType.STRING)
    private String userType;

    /** 用户邮箱 */
    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    @RuoyiExcel(name = "email", nameCode = MESSAGE_PREFIX + ".email", cellType = RuoyiExcel.ColumnType.STRING)
    private String email;

    /** 手机号码 */
    @RuoyiExcel(name = "phonenumber", nameCode = MESSAGE_PREFIX + ".phonenumber", cellType = RuoyiExcel.ColumnType.STRING)
    private String phonenumber;

    /** 用户性别 */
    @RuoyiExcel(name = "sex", nameCode = MESSAGE_PREFIX + ".sex", cellType = RuoyiExcel.ColumnType.STRING)
    private String sex;

    /** 用户头像 */
    private String avatar;

    /** 帐号状态（0正常 1停用） */
    @Getter
    @Setter
    @RuoyiExcel(name = "status", nameCode = MESSAGE_PREFIX + ".status", cellType = RuoyiExcel.ColumnType.STRING, handler = ExcelEnumValueHandler.class)
    private RuoyiEnableStatusEnums status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 最后登录IP */
    private String loginIp;

    /** 最后登录时间 */
    private Date loginDate;

    /** 密码最后更新时间 */
    private Date pwdUpdateDate;

    @Getter
    @Setter
    private String remark;

    /** 创建者 */
    @Getter
    @Setter
    private String createBy;

    /** 更新者 */
    @Getter
    @Setter
    private String updateBy;

    /** 部门对象 */
    private SysDept dept;

    private List<SysRole> roles;

    /** 角色组 */
    private Long[] roleIds;

    /** 岗位组 */
    private Long[] postIds;

    public SysUser()
    {

    }

    public SysUser(Long userId)
    {
        this.id = userId;
    }

    public Long getUserId()
    {
        return id;
    }

    public void setUserId(Long userId)
    {
        this.id = userId;
    }

    public boolean isAdmin()
    {
        return isAdmin(this.id);
    }

    public Long getDeptId()
    {
        return deptId;
    }

    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    public Long getParentId()
    {
        return parentId;
    }

    public void setParentId(Long parentId)
    {
        this.parentId = parentId;
    }

    public Long getRoleId()
    {
        return roleId;
    }

    public void setRoleId(Long roleId)
    {
        this.roleId = roleId;
    }

//    @NotBlank(message = "登录账号不能为空")
//    @Size(min = 0, max = 30, message = "登录账号长度不能超过30个字符")
//    public String getLoginName()
//    {
//        return loginName;
//    }
//
//    public void setLoginName(String loginName)
//    {
//        this.loginName = loginName;
//    }
//
//    @Size(min = 0, max = 30, message = "用户昵称长度不能超过30个字符")
//    public String getUserName()
//    {
//        return userName;
//    }
//
//    public void setUserName(String userName)
//    {
//        this.userName = userName;
//    }

    public String getUserType()
    {
        return userType;
    }

    public void setUserType(String userType)
    {
        this.userType = userType;
    }


    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPhonenumber()
    {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber)
    {
        this.phonenumber = phonenumber;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getLoginIp()
    {
        return loginIp;
    }

    public void setLoginIp(String loginIp)
    {
        this.loginIp = loginIp;
    }

    public Date getLoginDate()
    {
        return loginDate;
    }

    public void setLoginDate(Date loginDate)
    {
        this.loginDate = loginDate;
    }

    public Date getPwdUpdateDate()
    {
        return pwdUpdateDate;
    }

    public void setPwdUpdateDate(Date pwdUpdateDate)
    {
        this.pwdUpdateDate = pwdUpdateDate;
    }

    public SysDept getDept()
    {
        if (dept == null)
        {
            dept = new SysDept();
        }
        return dept;
    }

    public void setDept(SysDept dept)
    {
        this.dept = dept;
    }

    public List<SysRole> getRoles()
    {
        return roles;
    }

    public void setRoles(List<SysRole> roles)
    {
        this.roles = roles;
    }

    public Long[] getRoleIds()
    {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds)
    {
        this.roleIds = roleIds;
    }

    public Long[] getPostIds()
    {
        return postIds;
    }

    public void setPostIds(Long[] postIds)
    {
        this.postIds = postIds;
    }

    public Map<String, Object> getParams()
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        return params;
    }

    public void setParams(Map<String, Object> params)
    {
        this.params = params;
    }
}
