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
@ExcelEnumMessageCategory(CommunityConstants.ENUM_EXCEL_FIELD_PREFIX_RUOYI_ENABLE_STATUS)
public enum RuoyiEnableStatusEnums {
    // ruoyi 里面，0 表示 启动，1 表示 禁用
    ENABLE(0),
    DISABLE(1),
    ;

    @EnumValue
    @ExcelEnumMessageCode
    private final int code;

    RuoyiEnableStatusEnums(int code) {
        this.code = code;
    }
}
