package com.eb.mp.mysql.entity.business;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eb.constant.enums.EnableStatusEnums;
import lombok.Data;

import java.util.Date;

/**
 * @author suyh
 * @since 2024-09-02
 */
@Data
@TableName(value = "vip_account", autoResultMap = true)
public class VipAccountEntity {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * code
     */
    @TableField("code")
    private String code;

    /**
     * pnum
     */
    @TableField("pnum")
    private String pnum;

    /**
     * 账户
     */
    @TableField("account")
    private String account;

    /**
     * 图片
     */
    @TableField("img")
    private String img;

    /**
     * 使能[1-开启｜0-关闭]
     */
    @TableField("enable")
    private EnableStatusEnums enable;

    /**
     * 创建时间
     */
    @TableField("created")
    private Date created;

    /**
     * 更新时间
     */
    @TableField("updated")
    private Date updated;
}
