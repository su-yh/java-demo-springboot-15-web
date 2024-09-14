package com.eb.business.dto.exceptionorder.req;

import com.eb.constant.enums.OrderAuditStatusEnums;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * @author suyh
 * @since 2024-09-09
 */
@Data
public class ExceptionOrderAuditByIdUpdateReqDto {
    @Schema(description = "主键ID")
    @NotNull
    private Long id;

    @Schema(description = "订单审计状态")
    @NotNull
    private OrderAuditStatusEnums orderAuditStatus;

    @Size(max = 50)
    private String cpOrder;

    private BigDecimal amount;

    @Size(max = 20)
    private String pnum;

    @Size(max = 256)
    private String remark;
}
