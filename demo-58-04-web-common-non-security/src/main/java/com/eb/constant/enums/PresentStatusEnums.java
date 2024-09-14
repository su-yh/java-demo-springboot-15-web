package com.eb.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * @author suyh
 * @since 2024-09-06
 */
@Getter
public enum PresentStatusEnums {
    @Schema(description = "1-赠送中")
    RUNNING(1),
    @Schema(description = "2-赠送成功")
    SUCCESS(2),
    @Schema(description = "3-赠送失败")
    FAILED(3),

    ;

    @EnumValue
    private final int code;

    PresentStatusEnums(int code) {
        this.code = code;
    }
}
