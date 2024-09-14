package com.eb.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * @author suyh
 * @since 2024-09-09
 */
@Getter
public enum AuditRecordTypeEnums {
    @Schema(description = "1-买家提交")
    BUYER(1),
    @Schema(description = "2-后台录入")
    BACKEND(2),

    ;

    @EnumValue
    private final int code;

    AuditRecordTypeEnums(int code) {
        this.code = code;
    }
}
