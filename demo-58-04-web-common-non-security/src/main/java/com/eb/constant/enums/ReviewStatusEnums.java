package com.eb.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.eb.constant.CommunityConstants;
import com.eb.rouyi.excel.annotation.ExcelEnumMessageCategory;
import com.eb.rouyi.excel.annotation.ExcelEnumMessageCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * @author suyh
 * @since 2024-09-06
 */
@Getter
@ExcelEnumMessageCategory(CommunityConstants.ENUM_EXCEL_FIELD_PREFIX_REVIEW_STATUS)
public enum ReviewStatusEnums {
    @Schema(description = "1 待审核")
    WAITING(1),
    @Schema(description = "2 审核通过")
    APPROVED(2),
    @Schema(description = "3 审核拒绝")
    REJECTED(3),
    ;

    @EnumValue
    @ExcelEnumMessageCode
    private final int code;

    ReviewStatusEnums(int code) {
        this.code = code;
    }
}
