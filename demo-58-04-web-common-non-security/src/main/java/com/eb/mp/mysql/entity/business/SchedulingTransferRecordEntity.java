package com.eb.mp.mysql.entity.business;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eb.constant.enums.OrderAuditStatusEnums;
import com.eb.constant.enums.PayTypeEnums;
import com.eb.constant.enums.TransferStatusEnums;
import com.eb.group.ValidationGroups;
import com.eb.rouyi.excel.annotation.RuoyiExcel;
import com.eb.rouyi.excel.handler.ExcelEnumValueHandler;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author suyh
 * @since 2024-09-02
 */
@Data
@TableName(value = "scheduling_transfer_record", autoResultMap = true)
public class SchedulingTransferRecordEntity {
    public static final String MESSAGE_PREFIX = "excel.title.SchedulingTransferRecordEntity";

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键id")
    @NotNull(groups = ValidationGroups.Req.Update.class)
    private Long id;

    @TableField("code")
    private String code;

    @TableField("pnum")
    @RuoyiExcel(name = "pnum", nameCode = MESSAGE_PREFIX + ".pnum", cellType = RuoyiExcel.ColumnType.STRING)
    private String pnum;

    /**
     * 支付类型【1:tpp支付｜2:银行卡支付】
     */
    @TableField("pay_type")
    @Schema(description = "支付类型")
    private PayTypeEnums payType;

    /**
     * tpp
     */
    @TableField("tpp")
    private String tpp;

    /**
     * 银行卡号
     */
    @TableField("card_no")
    @RuoyiExcel(name = "cardNo", nameCode = MESSAGE_PREFIX + ".cardNo", cellType = RuoyiExcel.ColumnType.STRING)
    @Schema(description = "银行卡号")
    private String cardNo;

    /**
     * 银行账号
     */
    @TableField("account")
    @RuoyiExcel(name = "account", nameCode = MESSAGE_PREFIX + ".account", cellType = RuoyiExcel.ColumnType.STRING)
    @Schema(description = "银行账号")
    private String account;

    /**
     * swift
     */
    @TableField("swift")
    private String swift;

    /**
     * 买家uid
     */
    @TableField("receiver_uid")
    @Schema(description = "买家uid")
    private String receiverUid;

    /**
     * 购买信息 [{"sku":"SKU123","quantity":10,"price":99.99},{"sku":"SKU456","quantity":5,"price":59.99}]
     */
    @TableField("order_info")
    @RuoyiExcel(name = "orderInfo", nameCode = MESSAGE_PREFIX + ".orderInfo", cellType = RuoyiExcel.ColumnType.STRING)
    @Schema(description = "购买信息 [{\"sku\":\"SKU123\",\"quantity\":10,\"price\":99.99},{\"sku\":\"SKU456\",\"quantity\":5,\"price\":59.99}]")
    private String orderInfo;

    /**
     * 物流信息 [{"carrier":"usps","trackingNumber":"123456"},{"carrier":"ups","trackingNumber":"789012"}]
     */
    @TableField("logistics_info")
    @RuoyiExcel(name = "logisticsInfo", nameCode = MESSAGE_PREFIX + ".logisticsInfo", cellType = RuoyiExcel.ColumnType.STRING)
    @Schema(description = "物流信息 [{\"carrier\":\"usps\",\"trackingNumber\":\"123456\"},{\"carrier\":\"ups\",\"trackingNumber\":\"789012\"}]")
    private String logisticsInfo;

    /**
     * 金额
     */
    @TableField("amount")
    @RuoyiExcel(name = "amount", nameCode = MESSAGE_PREFIX + ".amount", cellType = RuoyiExcel.ColumnType.NUMERIC)
    @Schema(description = "金额")
    private BigDecimal amount;

    /**
     * 订单真实金额
     */
    @TableField("real_amount")
    @Schema(description = "订单真实金额")
    private BigDecimal realAmount;

    /**
     * cp订单号
     */
    @TableField("cp_order")
    @RuoyiExcel(name = "cpOrder", nameCode = MESSAGE_PREFIX + ".cpOrder", cellType = RuoyiExcel.ColumnType.STRING)
    @Schema(description = "cp订单号")
    private String cpOrder;

    /**
     * 我方生成订单号(= mchnt_order_no)
     */
    @TableField("svip_order_no")
    @RuoyiExcel(name = "svipOrderNo", nameCode = MESSAGE_PREFIX + ".svipOrderNo", cellType = RuoyiExcel.ColumnType.STRING)
    @Schema(description = "我方生成订单号(= mchnt_order_no)")
    private String svipOrderNo;

    /**
     * 我方生成订单号
     */
    @TableField("mchnt_order_no")
    @RuoyiExcel(name = "mchntOrderNo", nameCode = MESSAGE_PREFIX + ".mchntOrderNo", cellType = RuoyiExcel.ColumnType.STRING)
    @Schema(description = "我方生成订单号")
    private String mchntOrderNo;

    /**
     * vip的uid信息
     */
    @TableField("uid")
    @RuoyiExcel(name = "uid", nameCode = MESSAGE_PREFIX + ".uid", cellType = RuoyiExcel.ColumnType.STRING)
    @Schema(description = "vip的uid信息")
    private String uid;

    /**
     * 项目名称
     */
    @TableField("pn")
    @Schema(description = "项目名称")
    private String pn;

    @TableField("pkg")
    private String pkg;

    /**
     * 渠道号
     */
    @TableField("channel")
    @Schema(description = "渠道号")
    private String channel;

    /**
     * 状态（【1-等待中、2-成功（已付款已发货）、3-付款失败，4-已提交、 5-未发货， 6-代理商异常】）
     * 修改为： 1-调度中、2-成功（已付款已发货）、3-调度失败，4-已付款未发货, 5-审核中
     */
    @TableField("status")
    @RuoyiExcel(name = "status", nameCode = MESSAGE_PREFIX + ".status", cellType = RuoyiExcel.ColumnType.STRING, handler = ExcelEnumValueHandler.class)
    @Schema(description = "状态")
    private TransferStatusEnums status;

    /**
     * 权重分
     */
    @TableField("score")
    @Schema(description = "权重分")
    private Integer score;

    /**
     * 完成时间
     */
    @TableField("mtime")
    @RuoyiExcel(name = "mtime", nameCode = MESSAGE_PREFIX + ".mtime", cellType = RuoyiExcel.ColumnType.STRING)
    @Schema(description = "完成时间")
    private Date mtime;

    /**
     * 创建时间
     */
    @TableField("created")
    @RuoyiExcel(name = "created", nameCode = MESSAGE_PREFIX + ".created", cellType = RuoyiExcel.ColumnType.STRING, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    private Date created;

    /**
     * 更新时间
     */
    @TableField("updated")
    @Schema(description = "更新时间")
    private Date updated;

    /**
     * 过期时间
     */
    @TableField("expire_time")
    @Schema(description = "过期时间")
    private Date expireTime;

    /**
     * 日期
     */
    @TableField("dates")
    @RuoyiExcel(name = "dates", nameCode = MESSAGE_PREFIX + ".dates", cellType = RuoyiExcel.ColumnType.NUMERIC)
    @Schema(description = "日期")
    private Integer dates;

    /**
     * 赠品订单号
     */
    @TableField("transfer_order_no")
    @Schema(description = "赠品订单号")
    private String transferOrderNo;

    /**
     * UTR
     */
    @TableField("utr")
    @RuoyiExcel(name = "utr", nameCode = MESSAGE_PREFIX + ".utr", cellType = RuoyiExcel.ColumnType.STRING)
    private String utr;

    @TableField("order_audit_status")
    @RuoyiExcel(name = "order_audit_status", nameCode = MESSAGE_PREFIX + ".orderAuditStatus", cellType = RuoyiExcel.ColumnType.STRING, handler = ExcelEnumValueHandler.class)
    @Schema(description = "订单审计状态")
    private OrderAuditStatusEnums orderAuditStatus;

    @TableField("audit_user_id")
    @Schema(description = "异常订单审核人员ID")
    private Long auditUserId;

    @TableField("audit_user_nick")
    @Schema(description = "异常订单审核人员昵称")
    private String auditUserNick;

    @TableField("audit_remark")
    @Schema(description = "备注")
    private String auditRemark;


}
