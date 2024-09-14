package com.eb.mq.consumer.component;

import com.eb.mq.consumer.dto.MqMessageConsumerDto;
import com.eb.mq.consumer.dto.enums.MqMessageConsumerEnums;
import com.eb.tgbot.BotManager;
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
    private final BotManager botManager;

    // 接收rabbit mq队列里面的消息
    @RabbitListener(queues = "${spring.rabbitmq.svip.template.default-receive-queue}", containerFactory = "flinkContainerFactory")
    public void processMessage(MqMessageConsumerDto mqMessageConsumerDto, Channel channel, @Headers Map<String, Object> headers) {
        log.info("rabbitmq接收到业务方消息:{}", mqMessageConsumerDto.toString());

        try {
            // 处理消息
            if (mqMessageConsumerDto.getMessageCategoryCode() == MqMessageConsumerEnums.NOTICE.getCode()) {
                // 处理通知消息
                botManager.handleNoticeToAgent(mqMessageConsumerDto.getNoticeToAgent());
            } else {
                log.error("不支持的消息类型 mqMessageConsumerDto.getMessageCategoryCode():{}", mqMessageConsumerDto.getMessageCategoryCode());
            }

            channel.basicAck((long) headers.get(AmqpHeaders.DELIVERY_TAG), false);
        } catch (Exception e) {
            log.error("telegram bot 接收业务方消息处理异常:{}", e.getMessage());
        }
    }

}
