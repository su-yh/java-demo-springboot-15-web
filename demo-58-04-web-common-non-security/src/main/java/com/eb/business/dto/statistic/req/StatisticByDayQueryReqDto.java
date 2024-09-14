package com.eb.business.dto.statistic.req;

import com.eb.business.dto.DatesRangeQueryReqDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author suyh
 * @since 2024-09-04
 */
@Data
public class StatisticByDayQueryReqDto extends DatesRangeQueryReqDto {
    @Schema(description = "商户号(精确)")
    private String mchntNo;
}
