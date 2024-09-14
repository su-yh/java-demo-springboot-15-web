package com.eb.mq.consumer.dto;

import com.eb.mq.consumer.dto.business.NoticeToAgentMqDto;
import com.eb.mq.consumer.dto.enums.MqMessageConsumerEnums;
import lombok.Data;

/**
 * @author suyh
 * @since 2024-09-10
 */
@Data
public class MqMessageConsumerDto {
    /**
     * 消息分类
     *
     * @see MqMessageConsumerEnums
     */
    private Integer messageCategoryCode;

    /**
     * @see MqMessageConsumerEnums#NOTICE
     */
    private NoticeToAgentMqDto noticeToAgent;
}
