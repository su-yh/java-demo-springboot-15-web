package com.eb.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eb.constant.CommunityConstants;
import com.eb.rouyi.excel.annotation.ExcelEnumMessageCategory;
import com.eb.rouyi.excel.annotation.ExcelEnumMessageCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * @author suyh
 * @since 2024-09-03
 */
@Getter
@ExcelEnumMessageCategory(CommunityConstants.ENUM_EXCEL_FIELD_PREFIX_TRANSFER_STATUS)
public enum TransferStatusEnums {
    @Schema(description = "1-调度中")
    WAITING(1),
    @Schema(description = "2-成功（已付款已发货）")
    SUCCESS(2),
    @Schema(description = "3-调度失败")
    FAILED(3),
    @Schema(description = "4-已付款未发货")
    PAY_WAITING_DELIVER(4),
    @Schema(description = "5-审核中")
    AUDITING(5),
    ;

    @EnumValue
    @ExcelEnumMessageCode
    private final int code;

    TransferStatusEnums(int code) {
        this.code = code;
    }
}
