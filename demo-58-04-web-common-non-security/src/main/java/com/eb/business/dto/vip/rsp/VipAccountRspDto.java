package com.eb.business.dto.vip.rsp;

import com.baomidou.mybatisplus.annotation.TableField;
import com.eb.business.dto.sms.rsp.UuidRspDto;
import com.eb.constant.enums.EnableStatusEnums;
import com.eb.constant.enums.FinancialCategoryEnums;
import com.eb.constant.enums.ReviewStatusEnums;
import com.eb.rouyi.excel.annotation.RuoyiExcel;
import com.eb.rouyi.excel.handler.ExcelEnumValueHandler;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * @author suyh
 * @since 2024-09-03
 */
@Data
public class VipAccountRspDto implements UuidRspDto<VipAccountRspDto> {
    public static final String MESSAGE_PREFIX = "excel.title.VipAccountRspDto";

    private String uuid;

    @Schema(description = "tpp 表主键/银行卡表主键，放到一起不唯一")
    private Long id;

    @Schema(description = "手机号")
    @RuoyiExcel(name = "pnum", nameCode = MESSAGE_PREFIX + ".pnum", cellType = RuoyiExcel.ColumnType.STRING)
    private String pnum;

    @RuoyiExcel(name = "code", nameCode = MESSAGE_PREFIX + ".code", cellType = RuoyiExcel.ColumnType.STRING)
    private String code;

    @Schema(description = "金融账号类别：TPP账号/银行账号")
    @RuoyiExcel(name = "financialCategory", nameCode = MESSAGE_PREFIX + ".financialCategory", cellType = RuoyiExcel.ColumnType.STRING, handler = ExcelEnumValueHandler.class)
    private FinancialCategoryEnums financialCategory;

    @Schema(description = "金融账号")
    @RuoyiExcel(name = "financialAccount", nameCode = MESSAGE_PREFIX + ".financialAccount", cellType = RuoyiExcel.ColumnType.STRING)
    private String financialAccount;

    @RuoyiExcel(name = "bankAccountName", nameCode = MESSAGE_PREFIX + ".bankAccountName", cellType = RuoyiExcel.ColumnType.STRING)
    private String bankAccountName;

    @RuoyiExcel(name = "swiftCode", nameCode = MESSAGE_PREFIX + ".swiftCode", cellType = RuoyiExcel.ColumnType.STRING)
    private String swiftCode;

    @Schema(description = "状态")
    @RuoyiExcel(name = "enable", nameCode = MESSAGE_PREFIX + ".enable", cellType = RuoyiExcel.ColumnType.STRING, handler = ExcelEnumValueHandler.class)
    private EnableStatusEnums enable;

    @Schema(description = "人工审核状态")
    @TableField("review")
    @RuoyiExcel(name = "review", nameCode = MESSAGE_PREFIX + ".review", cellType = RuoyiExcel.ColumnType.STRING, handler = ExcelEnumValueHandler.class)
    private ReviewStatusEnums review;

    @Schema(description = "创建时间")
    @RuoyiExcel(name = "created", nameCode = MESSAGE_PREFIX + ".created", cellType = RuoyiExcel.ColumnType.STRING, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date created;

    @Schema(description = "更新时间")
    @RuoyiExcel(name = "updated", nameCode = MESSAGE_PREFIX + ".updated", cellType = RuoyiExcel.ColumnType.STRING, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date updated;

}
