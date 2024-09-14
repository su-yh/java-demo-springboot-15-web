package com.eb.business.dto.exceptionorder.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * @author suyh
 * @since 2024-09-09
 */
@Data
public class ExceptionOrderAuditByCpOrderReviseUpdateReqDto {
    @Schema(description = "主键ID")
    @NotNull
    private Long id;

    @Schema(description = "修正订单号")
    @Size(max = 50)
    @NotBlank
    private String cpOrderRevise;

    private BigDecimal amount;

    @Size(max = 20)
    private String pnum;
}
