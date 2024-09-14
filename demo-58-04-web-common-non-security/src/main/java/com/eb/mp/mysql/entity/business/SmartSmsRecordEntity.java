package com.eb.mp.mysql.entity.business;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eb.constant.enums.PayTypeEnums;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author suyh
 * @since 2024-09-07
 */
@Data
@TableName(value = "smart_sms_record", autoResultMap = true)
public class SmartSmsRecordEntity {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id; // 主键id

    private String pnum; // 代理手机号

//    private String address; // 发送人
//
//    private String body; // 短信内容

    private BigDecimal amount; // 短信金额

//    private String bankName; // 短信所属银行

    private Date created; // 创建时间

    private Integer dates; // 日期

    private Date sendTime; // 短信发送时间

    private Boolean hit; // 状态 (0未命中|1为命中)

    private String serialNo; // 序列号

    private String aid; // android_id

    private Integer smsId; // smsId

    private String utr; // utr

    private String tpp; // tpp

    private String cardNo; // 银行卡号

    private String account; // 银行用户名

    private String swift; // swift

    private PayTypeEnums payType; // 支付类型[1:tpp| 2:bank]

    private String cpOrder; // cp订单号

    private String svipOrderNo; // 我方订单号
}
