package com.leomaster.mp.mysql.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author suyh
 * @since 2024-08-31
 */
@Data
@TableName("user_info")
public class UserInfoEntity {
    @TableId(type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;

    private String nickname;

    private String salt;

    private Date created;

    private Date updated;
}
