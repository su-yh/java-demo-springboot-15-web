package com.eb.business.dto.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author suyh
 * @since 2024-09-09
 */
@Data
public class CpOrderBody {
    @Schema(description = "cpOrder")
    @NotNull
    private String cpOrder;
}
