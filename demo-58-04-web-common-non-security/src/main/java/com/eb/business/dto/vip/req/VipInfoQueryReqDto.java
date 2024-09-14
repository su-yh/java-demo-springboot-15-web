package com.eb.business.dto.vip.req;

import com.eb.constant.enums.OnlineStateEnums;
import com.eb.constant.enums.OrderEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author suyh
 * @since 2024-09-03
 */
@Data
public class VipInfoQueryReqDto {
    @Schema(description = "手机号(精确查询)")
    private String pnum;

    @Schema(description = "手机号(模糊查询)")
    private String pnumLike;

    @Schema(description = "(精确查询)")
    private String code;

    @Schema(description = "(模糊查询)")
    private String codeLike;

    @Schema(description = "svip代理自身上下线[0: 下线 ｜ 1: 上线]")
    private OnlineStateEnums svipAppOnline;

    @Schema(description = "金融账号(模糊查询)")
    private String financialAccountLike;

    @Schema(description = "余额范围：下限")
    private BigDecimal balanceLowerBound;

    @Schema(description = "余额范围：上限")
    private BigDecimal balanceUpperBound;

    @Schema(description = "余额排序")
    private OrderEnums orderBalance;

    @Schema(description = "总销量排序")
    private OrderEnums orderTransferBalance;

    @Schema(description = "发货订单数排序")
    private OrderEnums orderTransferCount;

    @Schema(description = "按时发货率排序")
    private OrderEnums orderTransferRate;

    @Schema(description = "平均每次发货时间排序")
    private OrderEnums orderTransferSeconds;
}
