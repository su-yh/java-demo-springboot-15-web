package com.eb.mq.consumer.dto.enums;

import lombok.Getter;

/**
 * @author suyh
 * @since 2024-09-10
 */
@Getter
public enum MqMessageConsumerEnums {
    /**
     * 业务端通知代理商消息
     */
    NOTICE(1)
    ;

    private final int code;

    MqMessageConsumerEnums(int code) {
        this.code = code;
    }
}
