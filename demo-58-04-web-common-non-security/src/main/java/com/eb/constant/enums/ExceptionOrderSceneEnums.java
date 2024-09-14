package com.eb.constant.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * @author suyh
 * @since 2024-09-09
 */
@Getter
public enum ExceptionOrderSceneEnums {
    @Schema(description = "情形1：1+0，匹配上了")
    MATCH(1),
    @Schema(description = "情形2：0+0，未匹配上")
    MISMATCH(2),
    ;
    private final int code;

    ExceptionOrderSceneEnums(int code) {
        this.code = code;
    }
}
