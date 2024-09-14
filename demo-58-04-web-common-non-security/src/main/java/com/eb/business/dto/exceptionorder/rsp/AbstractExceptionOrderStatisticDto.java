package com.eb.business.dto.exceptionorder.rsp;

import com.eb.business.dto.sms.rsp.UuidRspDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author suyh
 * @since 2024-09-10
 */
@Data
public abstract class AbstractExceptionOrderStatisticDto
        implements UuidRspDto<AbstractExceptionOrderStatisticDto> {
    private String uuid;

    private Integer dates;

    @Schema(description = "异常订单数")
    private Integer exceptionOrderCount;

    @Schema(description = "审核成功订单数")
    private Integer auditSuccessOrderCount;
//
//    @Schema(description = "审核通过率")
//    private BigDecimal auditSuccessRate;
}
