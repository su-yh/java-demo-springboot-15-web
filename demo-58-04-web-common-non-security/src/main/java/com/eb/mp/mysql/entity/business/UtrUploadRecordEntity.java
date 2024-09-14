package com.eb.mp.mysql.entity.business;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eb.constant.enums.AuditRecordTypeEnums;
import com.eb.constant.enums.OrderMatchStatusEnums;
import com.eb.group.ValidationGroups;
import com.eb.rouyi.excel.annotation.RuoyiExcel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author suyh
 * @since 2024-09-09
 */
@TableName(value = "utr_upload_record", autoResultMap = true)
@Data
public class UtrUploadRecordEntity {
    public static final String MESSAGE_PREFIX = "excel.title.UtrUploadRecordEntity";

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Null(groups = ValidationGroups.Req.Create.class)
    private Long id;

    /**
     * pn
     */
    @TableField("pn")
    @Size(max = 20)
    private String pn;

    /**
     * 买家uid
     */
    @NotNull(groups = ValidationGroups.Req.Create.class)
    @Size(max = 20)
    @Schema(description = "买家uid")
    @TableField("buyer_uid")
    @RuoyiExcel(name = "buyerUid", nameCode = MESSAGE_PREFIX + ".buyerUid", cellType = RuoyiExcel.ColumnType.STRING)
    private String buyerUid;

    /**
     * 代理手机号
     */
    @TableField("pnum")
    @Schema(description = "代理手机号")
    @Size(max = 20)
    private String pnum;

    /**
     * 审计录入类型[1-买家提交 ｜ 2-后台录入]
     */
    @TableField("type")
    @Schema(description = "审计录入类型")
    private AuditRecordTypeEnums type;

    /**
     * cp_order
     */
    @Size(max = 50)
    @TableField("cp_order")
    @Schema(description = "cp_order")
    @RuoyiExcel(name = "cpOrder", nameCode = MESSAGE_PREFIX + ".cpOrder", cellType = RuoyiExcel.ColumnType.STRING)
    private String cpOrder;

    @Size(max = 50)
    @TableField("cp_order_revise")
    @Schema(description = "修正订单号")
    private String cpOrderRevise;

    /**
     * 订单金额
     */
    @TableField("amount")
    @Schema(description = "订单金额")
    @RuoyiExcel(name = "amount", nameCode = MESSAGE_PREFIX + ".amount", cellType = RuoyiExcel.ColumnType.NUMERIC)
    private BigDecimal amount;

    /**
     * svip订单号
     */
    @TableField("svip_order_no")
    @Schema(description = "svip订单号")
    @Size(max = 50)
    private String svipOrderNo;

    /**
     * 录入的utr
     */
    @Size(max = 50)
    @TableField("utr")
    @RuoyiExcel(name = "utr", nameCode = MESSAGE_PREFIX + ".utr", cellType = RuoyiExcel.ColumnType.STRING)
    private String utr;

    /**
     * 付款截图
     */
    @TableField("pay_screenshot_url")
    @Schema(description = "付款截图(传文件上传返回的url路径字符串，前端通过IP端口以及上下文根路径进行拼接)")
    @Size(max = 1000)
    private String payScreenshotUrl;

    /**
     * 付款确认金额(买家提交 ｜ 后台录入）
     */
    @TableField("pay_amount")
    @Schema(description = "付款确认金额(买家提交 ｜ 后台录入）")
    private BigDecimal payAmount;

    /**
     * 订单匹配状态[0-未匹配 ｜ 1-已匹配]
     */
    @TableField("hit")
    @Schema(description = "订单匹配状态")
    private OrderMatchStatusEnums hit;

    /**
     * 日期
     */
    @TableField("dates")
    @RuoyiExcel(name = "dates", nameCode = MESSAGE_PREFIX + ".dates", cellType = RuoyiExcel.ColumnType.NUMERIC)
    private Integer dates;

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
