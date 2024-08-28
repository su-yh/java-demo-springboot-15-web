package com.leomaster.mp.entity.mysql;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("tb_user_login")
@Data
public class TbUserLoginEntity extends AbstractUserEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 1：用户注册；2：用户登录
     */
    private Integer src;

    /**
     * 创建时间
     */
    private Long ctime;

    /**
     * 原始渠道
     */
    private String originChannel;

    /**
     * 原数据id
     */
    private Long vungoUserLoginId;

    /**
     * 日期
     */
    private Integer day;

    /**
     * 数据入库时间
     */
    private Long cts;

    /**
     * pn
     */
    private String pn;

}
