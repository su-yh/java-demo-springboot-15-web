package com.eb.business.dto.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author suyh
 * @since 2024-09-12
 */
@Data
public class SingleStringBody {
    @Schema(description = "单字符串body 结构")
    @NotNull
    private String body;
}
