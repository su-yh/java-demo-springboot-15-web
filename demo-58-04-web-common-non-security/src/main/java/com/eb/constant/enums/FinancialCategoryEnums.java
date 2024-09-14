package com.eb.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eb.constant.CommunityConstants;
import com.eb.rouyi.excel.annotation.ExcelEnumMessageCategory;
import com.eb.rouyi.excel.annotation.ExcelEnumMessageCode;
import lombok.Getter;

/**
 * @author suyh
 * @since 2024-09-03
 */
@Getter
@ExcelEnumMessageCategory(CommunityConstants.ENUM_EXCEL_FIELD_PREFIX_FINANCIAL_CATEGORY)
public enum FinancialCategoryEnums {
    TPP("TPP", 1),
    BANK("BANK", 2),
    ;

    @EnumValue
    @ExcelEnumMessageCode
    private final String code;

    private final int codeEnum;

    FinancialCategoryEnums(String code, int codeEnum) {
        this.code = code;
        this.codeEnum = codeEnum;
    }
}
