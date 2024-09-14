package com.eb.mp.mysql.entity.custom;

import com.eb.constant.enums.OrderAuditStatusEnums;
import com.eb.constant.enums.PayTypeEnums;
import com.eb.constant.enums.TransferStatusEnums;
import com.eb.mp.mysql.entity.business.UtrUploadRecordEntity;
import com.eb.rouyi.excel.annotation.RuoyiExcel;
import com.eb.rouyi.excel.handler.ExcelEnumValueHandler;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author suyh
 * @since 2024-09-09
 */
@Data
public class ExceptionOrderEntity extends UtrUploadRecordEntity {
    public static final String MESSAGE_PREFIX = "excel.title.ExceptionOrderEntity";

    @Schema(description = "scheduling_transfer_record 表的主键ID，它的值存在则匹配到订单")
    private Long orderId;

    @RuoyiExcel(name = "code", nameCode = MESSAGE_PREFIX + ".code", cellType = RuoyiExcel.ColumnType.STRING)
    private String code;

    @RuoyiExcel(name = "tpp", nameCode = MESSAGE_PREFIX + ".tpp", cellType = RuoyiExcel.ColumnType.STRING)
    private String tpp;

    @Schema(description = "银行卡号")
    @RuoyiExcel(name = "cardNo", nameCode = MESSAGE_PREFIX + ".cardNo", cellType = RuoyiExcel.ColumnType.STRING)
    private String cardNo;

    @Schema(description = "订单审计状态")
    private OrderAuditStatusEnums orderAuditStatus;

    @Schema(description = "状态")
    @RuoyiExcel(name = "status", nameCode = MESSAGE_PREFIX + ".status", cellType = RuoyiExcel.ColumnType.STRING)
    private TransferStatusEnums status;

    @Schema(description = "支付类型")
    @RuoyiExcel(name = "payType", nameCode = MESSAGE_PREFIX + ".payType", cellType = RuoyiExcel.ColumnType.STRING, handler = ExcelEnumValueHandler.class)
    private PayTypeEnums payType;

    @Schema(description = "金融账号：TPP/银行卡号")
   private String financialAccount;

    // 返回给前端用的，基于支付类型，判断金融账号
    public String getFinancialAccount() {
        if (payType == null) {
            return null;
        }

        financialAccount = null;
        switch (payType) {
            case TPP:
                financialAccount = tpp;
                break;
            case BANK:
                financialAccount = cardNo;
                break;
        }

        return financialAccount;
    }
}
