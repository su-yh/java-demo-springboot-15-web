package com.eb.business.dto.exceptionorder.req;

import com.eb.business.dto.DatesRangeQueryReqDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author suyh
 * @since 2024-09-10
 */
@Data
public class ExceptionOrderStatisticByUserQueryReqDto
        extends DatesRangeQueryReqDto {
    @Schema(description = "运营人员昵称(模糊)")
    private String nickNameLike;
}
