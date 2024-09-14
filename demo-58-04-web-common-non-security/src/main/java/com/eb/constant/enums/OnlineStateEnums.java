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
@ExcelEnumMessageCategory(CommunityConstants.ENUM_EXCEL_FIELD_PREFIX_ONLINE_STATE)
public enum OnlineStateEnums {
    ONLINE(1),
    OFFLINE(0),
    ;

    @EnumValue
    @ExcelEnumMessageCode
    private final int code;

    OnlineStateEnums(int code) {
        this.code = code;
    }
}
