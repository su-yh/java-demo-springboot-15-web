package com.eb.business.dto.exceptionorder.req;

import com.eb.business.dto.DatesRangeQueryReqDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author suyh
 * @since 2024-09-09
 */
@Data
public class SchedulingOrderQueryReqDto extends DatesRangeQueryReqDto {
    @Schema(description = "cp订单号(精确)")
    private String cpOrder;

    @Schema(description = "金额(精确)")
    private BigDecimal amount;

    @Schema(description = "收款时间(时间戳)")
    private Long created;

    @Schema(description = "code(精确)")
    private String code;

    @Schema(description = "买家uid(精确)")
    private String receiverUid;

    @Schema(description = "手机号(精确)")
    private String pnum;
}
