package com.leomaster.mp.entity.mysql;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@TableName("tb_recharge")
@Data
public class TbRechargeEntity extends AbstractUserEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单创建时间
     */
    private Long ctime;

    /**
     * 商品额度，卢比
     */
    private BigDecimal goodsAmt;

    /**
     * 充值类型
     */
    private String chips;

    /**
     * 原数据id
     */
    private Long vungoRechargeId;

    /**
     * 原始渠道
     */
    private String originChannel;

    /**
     * 日期
     */
    private Integer day;

    /**
     * 订单号
     */
    @TableField("`order`")
    private String order;

    /**
     * 数据入库时间
     */
    private Long cts;

    /**
     * pn
     */
    private String pn;

    /**
     * 订单修改时间
     */
    private Long mtime;

    /**
     * 登录渠道号
     */
    private String loginChannel;

    /**
     * 注册渠道号
     */
    private String registerChannel;
}
