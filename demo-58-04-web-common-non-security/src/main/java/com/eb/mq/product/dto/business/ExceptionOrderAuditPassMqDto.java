package com.eb.mq.product.dto.business;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author suyh
 * @since 2024-09-10
 */
@Data
public class ExceptionOrderAuditPassMqDto {
    private Long loginUserId;
    private String loginNickName;

    private String cpOrder;

    private BigDecimal amount;

    private String pnum;

    private String remark;
}
