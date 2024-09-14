package com.eb.mp.mysql.entity.business;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eb.constant.enums.EnableStatusEnums;
import com.eb.constant.enums.ReviewStatusEnums;
import lombok.Data;

import java.util.Date;

/**
 * @author suyh
 * @since 2024-09-02
 */
@Data
@TableName(value = "vip_bank_account", autoResultMap = true)
public class VipBankAccountEntity {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 代理code
     */
    @TableField("code")
    private String code;

    /**
     * 代理手机号
     */
    @TableField("pnum")
    private String pnum;

    /**
     * 银行卡号
     */
    @TableField("card_no")
    private String cardNo;

    /**
     * 银行账号
     */
    @TableField("account")
    private String account;

    /**
     * swift
     */
    @TableField("swift")
    private String swift;

    /**
     * 使能【1:开启｜0:关闭】
     */
    @TableField("enable")
    private EnableStatusEnums enable;

    /**
     * 人工审核状态
     */
    @TableField("review")
    private ReviewStatusEnums review;

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
