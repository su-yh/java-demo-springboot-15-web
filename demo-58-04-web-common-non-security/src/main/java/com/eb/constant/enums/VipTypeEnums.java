package com.eb.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * @author suyh
 * @since 2024-09-03
 */
@Getter
public enum VipTypeEnums {
    @Schema(description = "1自营")
    SELF(1),
    @Schema(description = "2代理")
    PROXY(2),
    ;

    @EnumValue
    private final int code;

    VipTypeEnums(int code) {
        this.code = code;
    }
}
