package com.eb.mq.consumer.component;

import com.eb.mq.consumer.dto.MqMessageConsumerDto;
import com.eb.mq.consumer.dto.enums.MqMessageConsumerEnums;
import com.eb.util.JsonUtils;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class MqConsumerComponent {
    // 接收rabbit mq队列里面的消息
    @RabbitListener(queues = "${spring.rabbitmq.template.default-receive-queue}", containerFactory = "rabbitListenerContainerFactory")
    public void processMessage(MqMessageConsumerDto mqMessageConsumerDto, Channel channel, @Headers Map<String, Object> headers) {
        try {
            // 处理消息
            if (mqMessageConsumerDto.getMessageCategoryCode() == MqMessageConsumerEnums.NOTICE.getCode()) {
                // 处理通知消息
                System.out.println(JsonUtils.serializable(mqMessageConsumerDto.getNoticeToAgent()));
            } else {
                log.error("不支持的消息类型 mqMessageConsumerDto.getMessageCategoryCode():{}", mqMessageConsumerDto.getMessageCategoryCode());
            }

            // 手动ACK 才需要
            channel.basicAck((long) headers.get(AmqpHeaders.DELIVERY_TAG), false);
        } catch (Exception e) {
            log.error("consumer mq failed.", e);
        }
    }

}
