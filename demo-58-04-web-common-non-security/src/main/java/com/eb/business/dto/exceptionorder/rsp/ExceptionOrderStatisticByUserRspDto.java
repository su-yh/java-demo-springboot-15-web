package com.eb.business.dto.exceptionorder.rsp;

import lombok.Data;

/**
 * @author suyh
 * @since 2024-09-10
 */
@Data
public class ExceptionOrderStatisticByUserRspDto
        extends AbstractExceptionOrderStatisticDto {

//    private String userId;

    private String userNickName;
}
