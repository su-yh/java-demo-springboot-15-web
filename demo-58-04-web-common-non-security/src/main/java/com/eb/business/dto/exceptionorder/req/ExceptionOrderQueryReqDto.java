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
public class ExceptionOrderQueryReqDto extends DatesRangeQueryReqDto {

    @Schema(description = "商户号(模糊)")
    private String mchntNoLike;

    @Schema(description = "cp订单号(模糊)")
    private String cpOrderLike;

    @Schema(description = "代理code(模糊)")
    private String codeLike;

    @Schema(description = "代理手机号(模糊)")
    private String pnumLike;

    @Schema(description = "买家UID(模糊)")
    private String buyerUidLike;

    @Schema(description = "金额范围：下限")
    private BigDecimal amountLowerBound;

    @Schema(description = "金额范围：上限")
    private BigDecimal amountUpperBound;

    @Schema(description = "金融账号：TPP/银行卡号(模糊查询)")
    private String financialAccountLike;

    private String utrLike;
}
