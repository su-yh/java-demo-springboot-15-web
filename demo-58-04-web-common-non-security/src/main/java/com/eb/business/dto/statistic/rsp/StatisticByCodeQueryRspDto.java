package com.eb.business.dto.statistic.rsp;

import com.eb.business.dto.sms.rsp.UuidRspDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author suyh
 * @since 2024-09-04
 */
@Data
public class StatisticByCodeQueryRspDto
        extends AbstractStatisticQueryRspDto
        implements UuidRspDto<StatisticByCodeQueryRspDto> {
    private String uuid;

    @Schema(description = "代理商code")
    private String code;

    @Schema(description = "失败订单数(用于计算的)")
    private Integer orderFailedNumber;

    @Schema(description = "成功订单数(用于计算的)")
    private Integer orderSuccessNumber;

    @Schema(description = "(mtime - created 的差值累计总和，单位：毫秒)(用于计算的)")
    private Long msTotal;
}
