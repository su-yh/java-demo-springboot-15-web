package com.eb.business.dto.vip.rsp;

import com.eb.constant.enums.FinancialCategoryEnums;
import com.eb.constant.enums.OnlineStateEnums;
import com.eb.constant.enums.SvipAuditStatusEnums;
import com.eb.rouyi.excel.annotation.RuoyiExcel;
import com.eb.rouyi.excel.handler.ExcelEnumValueHandler;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author suyh
 * @since 2024-09-03
 */
@Data
public class VipInfoRspDto {
    public static final String MESSAGE_PREFIX = "excel.title.VipInfoRspDto";

    private Long id;

    @RuoyiExcel(name = "tgUsername", cellType = RuoyiExcel.ColumnType.STRING)
    private String tgUsername;

    @RuoyiExcel(name = "email", cellType = RuoyiExcel.ColumnType.STRING)
    private String email;

    @Schema(description = "电话号码")
    @RuoyiExcel(name = "pnum", nameCode = MESSAGE_PREFIX + ".pnum", cellType = RuoyiExcel.ColumnType.STRING)
    private String pnum;

    @Schema(description = "余额")
    @RuoyiExcel(name = "balance", nameCode = MESSAGE_PREFIX + ".balance", cellType = RuoyiExcel.ColumnType.NUMERIC)
    private BigDecimal balance;

    @RuoyiExcel(name = "name", nameCode = MESSAGE_PREFIX + ".name", cellType = RuoyiExcel.ColumnType.STRING)
    private String name;

    @Schema(description = "总销售额")
    @RuoyiExcel(name = "transferBalance", nameCode = MESSAGE_PREFIX + ".transferBalance", cellType = RuoyiExcel.ColumnType.NUMERIC)
    private BigDecimal transferBalance;

    @Schema(description = "发货订单数")
    @RuoyiExcel(name = "transferCount", nameCode = MESSAGE_PREFIX + ".transferCount", cellType = RuoyiExcel.ColumnType.NUMERIC)
    private Integer transferCount;

    @Schema(description = "svip代理自身上下线[0: 下线 ｜ 1: 上线]")
    @RuoyiExcel(name = "svipAppOnline", nameCode = MESSAGE_PREFIX + ".svipAppOnline", cellType = RuoyiExcel.ColumnType.STRING, handler = ExcelEnumValueHandler.class)
    private OnlineStateEnums svipAppOnline;

    @RuoyiExcel(name = "contactDetails", nameCode = MESSAGE_PREFIX + ".contactDetails", cellType = RuoyiExcel.ColumnType.STRING)
    private String contactDetails;

    @Schema(description = "金融账号类别：TPP账号/银行账号")
    private FinancialCategoryEnums financialCategory;

    @Schema(description = "金融账号")
    @RuoyiExcel(name = "financialAccount", nameCode = MESSAGE_PREFIX + ".financialAccount", cellType = RuoyiExcel.ColumnType.STRING)
    private String financialAccount;

    @Schema(description = "按时发货率")
    @RuoyiExcel(name = "transferRate", nameCode = MESSAGE_PREFIX + ".transferRate", cellType = RuoyiExcel.ColumnType.NUMERIC)
    private BigDecimal transferRate;

    @Schema(description = "平均每次发货时间，单位：秒")
    @RuoyiExcel(name = "transferSeconds", nameCode = MESSAGE_PREFIX + ".transferSeconds", cellType = RuoyiExcel.ColumnType.NUMERIC)
    private Integer transferSeconds;

    @Schema(description = "是否审核通过 ( 0 未提交审核 | 1 审核中 | 2 审核通过 | 3 审核失败 | 4 已注销冻结）")
    @RuoyiExcel(name = "svipAuditStatus", nameCode = MESSAGE_PREFIX + ".svipAuditStatus", cellType = RuoyiExcel.ColumnType.NUMERIC, handler = ExcelEnumValueHandler.class)
    private SvipAuditStatusEnums svipAuditStatus;

    @Schema(description = "svip系统上下线状态[0: 下线 ｜ 1: 上线]")
    @RuoyiExcel(name = "svipSystemOnline", nameCode = MESSAGE_PREFIX + ".svipSystemOnline", cellType = RuoyiExcel.ColumnType.NUMERIC, handler = ExcelEnumValueHandler.class)
    private OnlineStateEnums svipSystemOnline;


}
