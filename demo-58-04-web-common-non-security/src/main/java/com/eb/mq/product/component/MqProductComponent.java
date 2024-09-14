package com.eb.mq.product.component;

import com.eb.event.MqMessageEvent;
import com.eb.mq.product.dto.MqMessageProductDto;
import com.eb.mq.product.dto.business.FinancialAccountModifyMqDto;
import com.eb.mq.product.dto.enums.MqMessageEventCategoryEnums;
import com.eb.mq.product.dto.enums.OptActionEnums;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;

import javax.annotation.PostConstruct;

/**
 * @author suyh
 * @since 2024-09-07
 */
@RequiredArgsConstructor
@Slf4j
public class MqProductComponent {
    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.svip.template.exchange}")
    @Setter
    private String exchange;

    @Value("${spring.rabbitmq.svip.template.routing-key}")
    @Setter
    private String routingKey;

    @EventListener(MqMessageEvent.class)
    public void mqMessageProductEvent(MqMessageEvent event) {
        rabbitTemplate.convertAndSend(exchange, routingKey, event.getMessageProductDto());
    }

    // suyh - 测试用的
    @PostConstruct
    public void init() {
        MqMessageProductDto dto = new MqMessageProductDto();
        dto.setMessageCategoryCode(MqMessageEventCategoryEnums.VIP_INFO_SVIP_APP_ONLINE.getCode());
        FinancialAccountModifyMqDto message = new FinancialAccountModifyMqDto(OptActionEnums.CREATE.getCode());
        dto.setMessageFinancialAccountModify(message);
        rabbitTemplate.convertAndSend(exchange, routingKey, dto);
    }
}
