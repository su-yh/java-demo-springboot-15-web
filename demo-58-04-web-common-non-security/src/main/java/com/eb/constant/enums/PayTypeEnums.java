package com.eb.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eb.constant.CommunityConstants;
import com.eb.rouyi.excel.annotation.ExcelEnumMessageCategory;
import com.eb.rouyi.excel.annotation.ExcelEnumMessageCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * @author suyh
 * @since 2024-09-04
 */
@Getter
@ExcelEnumMessageCategory(CommunityConstants.ENUM_EXCEL_FIELD_PREFIX_PAY_TYPE)
public enum PayTypeEnums {
    @Schema(description = "1:tpp支付")
    TPP(1),
    @Schema(description = "2:银行卡支付")
    BANK(2),
    ;

    @EnumValue
    @ExcelEnumMessageCode
    private final int code;

    PayTypeEnums(int code) {
        this.code = code;
    }
}
