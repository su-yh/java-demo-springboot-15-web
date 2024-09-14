package com.eb.mq.consumer.dto.business;

import com.eb.mq.consumer.dto.MqConsumerBaseDto;
import lombok.Data;

/**
 * @author suyh
 * @since 2024-09-10
 */
@Data
public class NoticeToAgentMqDto implements MqConsumerBaseDto {
    private String code;
    private String pnum;
    private String message;
}
