package com.eb.mq.product.dto.enums;

import lombok.Getter;

/**
 * @author suyh
 * @since 2024-09-07
 */
@Getter
public enum MqMessageEventCategoryEnums {
    // 金融账号修改或者新增
    FINANCIAL_ACCOUNT_MODIFY(1),

    // 代理商信息上下线
    VIP_INFO_SVIP_APP_ONLINE(2),

    // 异常订单审核通过
    EXCEPTION_ORDER_AUDIT_SUCCESS(3),
    ;

    private final int code;
    MqMessageEventCategoryEnums(int code) {
        this.code = code;
    }
}
