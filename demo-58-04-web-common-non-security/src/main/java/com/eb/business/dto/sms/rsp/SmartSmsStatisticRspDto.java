package com.eb.business.dto.sms.rsp;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author suyh
 * @since 2024-09-07
 */
@Data
public class SmartSmsStatisticRspDto implements UuidRspDto<SmartSmsStatisticRspDto> {
    private String uuid;

    private Integer dates;
    private String pnum;
    private BigDecimal amount;
}
