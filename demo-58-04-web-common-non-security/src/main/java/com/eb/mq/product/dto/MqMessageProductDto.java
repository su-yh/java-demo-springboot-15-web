package com.eb.mq.product.dto;

import com.eb.mq.product.dto.business.ExceptionOrderAuditPassMqDto;
import com.eb.mq.product.dto.business.FinancialAccountModifyMqDto;
import com.eb.mq.product.dto.enums.MqMessageEventCategoryEnums;
import lombok.Data;

/**
 * @author suyh
 * @since 2024-09-07
 */
@Data
public class MqMessageProductDto {
    /**
     * 共用一个mq 的routing_key，每一种消息都对应一个枚举分类，每一个枚举分类对应一个下面的属性字段。
     *
     * @see MqMessageEventCategoryEnums
     */
    private Integer messageCategoryCode;

    /**
     * 金融账号修改或者新增
     * @see MqMessageEventCategoryEnums#FINANCIAL_ACCOUNT_MODIFY
     */
    private FinancialAccountModifyMqDto messageFinancialAccountModify;

    /**
     * 异常订单审核通过
     * @see MqMessageEventCategoryEnums#EXCEPTION_ORDER_AUDIT_SUCCESS
     */
    private ExceptionOrderAuditPassMqDto exceptionOrderAuditPass;


}
