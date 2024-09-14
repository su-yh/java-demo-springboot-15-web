package com.eb.business.dto.order.req;

import com.eb.business.dto.DatesRangeQueryReqDto;
import com.eb.constant.enums.TransferStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author suyh
 * @since 2024-09-04
 */
@Data
public class SchedulingTransferRecordQueryReqDto extends DatesRangeQueryReqDto {
    @Schema(description = "订单状态")
    private TransferStatusEnums status;

    @Schema(description = "商户号(模糊)")
    private String mchntNoLike;

    @Schema(description = "cp订单号(模糊)")
    private String cpOrderLike;

    @Schema(description = "我方生成订单号(= mchnt_order_no)(模糊)")
    private String svipOrderNoLike;

    @Schema(description = "我方生成订单号(模糊)")
    private String mchntOrderNoLike;

    @Schema(description = "买家uid(模糊)")
    private String uidLike;

    @Schema(description = "手机号(模糊)")
    private String pnumLike;

    private String utrLike;

    @Schema(description = "TPP/银行卡号(模糊)")
    private String financialAccountLike;
}
