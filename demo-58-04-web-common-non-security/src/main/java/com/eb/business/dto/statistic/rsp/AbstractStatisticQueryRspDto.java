package com.eb.business.dto.statistic.rsp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author suyh
 * @since 2024-09-04
 */
@Data
public abstract class AbstractStatisticQueryRspDto {
    @Schema(description = "日期")
    private Integer dates;

    @Schema(description = "总订单数")
    private Integer orderTotalNumber;

    @Schema(description = "收货订单数")
    private Integer orderReceiveNumber;

    @Schema(description = "销售额")
    private BigDecimal orderTotalAmount;

    @Schema(description = "发货率")
    private BigDecimal transferRate;

    @Schema(description = "平均响应时间（单位s）")
    private Integer transferSeconds;
}
