package com.eb.business.dto.statistic.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author suyh
 * @since 2024-09-04
 */
@Data
public class StatisticByCodeQueryReqDto {
    @Schema(description = "日期，格式：yyyyMMdd")
    @NotNull
    private Integer dates;

//    @Schema(description = "代理商code(模糊)")
//    private String codeLike;

    @Schema(description = "代理商code(精确)")
    private String code;
}
