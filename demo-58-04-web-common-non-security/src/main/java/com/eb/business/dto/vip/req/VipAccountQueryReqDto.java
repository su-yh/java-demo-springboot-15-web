package com.eb.business.dto.vip.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author suyh
 * @since 2024-09-03
 */
@Data
public class VipAccountQueryReqDto {
    @Schema(description = "手机号(模糊查询)")
    private String pnumLike;

    @Schema(description = "(精确查询)")
    private String code;

    @Schema(description = "(模糊查询)")
    private String codeLike;

    @Schema(description = "金融账号：TPP/银行卡号(模糊查询)")
    private String financialAccountLike;
}
