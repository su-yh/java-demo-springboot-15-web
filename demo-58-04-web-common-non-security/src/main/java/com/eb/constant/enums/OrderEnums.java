package com.eb.constant.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author suyh
 * @since 2024-09-04
 */
public enum OrderEnums {
    @Schema(description = "升序")
    ascend,
    @Schema(description = "降序")
    descend,
}
