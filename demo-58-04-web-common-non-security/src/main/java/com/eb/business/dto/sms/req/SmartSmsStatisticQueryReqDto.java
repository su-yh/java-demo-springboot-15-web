package com.eb.business.dto.sms.req;

import com.eb.business.dto.DatesRangeQueryReqDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author suyh
 * @since 2024-09-07
 */
@Data
public class SmartSmsStatisticQueryReqDto extends DatesRangeQueryReqDto {

    @Schema(description = "代理手机号(模糊)")
    private String pnumLike; // 代理手机号
}
