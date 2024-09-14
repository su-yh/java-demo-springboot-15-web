package com.eb.business.dto.vip.req;

import com.eb.constant.enums.OnlineStateEnums;
import com.eb.group.ValidationGroups;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author suyh
 * @since 2024-09-03
 */
@Data
public class VipInfoUpdateReqDto {
    @NotNull(groups = ValidationGroups.Req.Update.class)
    private Long id;

    private OnlineStateEnums svipAppOnline;

    @Schema(description = "tg用户名")
    @Size(min = 5, max = 32)
    private String tgUsername;

    @Schema(description = "Email")
    @Size(max = 320)
    private String email;

}
