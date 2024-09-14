package com.eb.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * @author suyh
 * @since 2024-09-09
 */
@Getter
public enum OrderMatchStatusEnums {
    @Schema(description = "0-未匹配")
    MISMATCH(0),
    @Schema(description = "1-已匹配")
    MATCH(1),
    ;

    @EnumValue
    private final int code;

    OrderMatchStatusEnums(int code) {
        this.code = code;
    }
}
