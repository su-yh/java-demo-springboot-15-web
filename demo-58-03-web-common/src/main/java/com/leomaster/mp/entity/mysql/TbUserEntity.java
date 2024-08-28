package com.leomaster.mp.entity.mysql;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;

// 新用户注册表记录
@TableName("tb_user")
@Data
public class TbUserEntity extends AbstractUserEntity {
    private static final long serialVersionUID = 2969412559718218965L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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
    private Long vungoUserId;

    /**
     * 日期
     */
    @NotNull
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
