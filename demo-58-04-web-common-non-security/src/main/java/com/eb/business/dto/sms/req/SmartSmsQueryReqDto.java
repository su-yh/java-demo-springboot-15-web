package com.eb.business.dto.sms.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author suyh
 * @since 2024-09-07
 */
@Data
public class SmartSmsQueryReqDto {
    private Integer dates; // 日期

    @Schema(description = "代理手机号(模糊)")
    private String pnumLike; // 代理手机号

    @Schema(description = "金额下限")
    private BigDecimal amountLower; // 短信金额

    @Schema(description = "金额上限")
    private BigDecimal amountUpper; // 短信金额

    @Schema(description = "utr(模糊)")
    private String utrLike;

    @Schema(description = "银行卡(模糊)")
    private String cardNoLike;
}
