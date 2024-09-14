package com.eb.mq.product.component;

import com.eb.event.MqMessageEvent;
import com.eb.mq.product.dto.MqMessageProductDto;
import com.eb.mq.product.dto.business.FinancialAccountModifyMqDto;
import com.eb.mq.product.dto.enums.MqMessageEventCategoryEnums;
import com.eb.mq.product.dto.enums.OptActionEnums;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.event.EventListener;

/**
 * @author suyh
 * @since 2024-09-07
 */
@RequiredArgsConstructor
@Slf4j
public class MqProductComponent {
    private final RabbitTemplate rabbitTemplate;

    @EventListener(MqMessageEvent.class)
    public void mqMessageProductEvent(MqMessageEvent event) {
        rabbitTemplate.convertAndSend(event.getMessageProductDto());
    }

    // suyh - 测试用的
    // @PostConstruct
    public void init() {
        MqMessageProductDto dto = new MqMessageProductDto();
        dto.setMessageCategoryCode(MqMessageEventCategoryEnums.VIP_INFO_SVIP_APP_ONLINE.getCode());
        FinancialAccountModifyMqDto message = new FinancialAccountModifyMqDto(OptActionEnums.CREATE.getCode());
        dto.setMessageFinancialAccountModify(message);
        rabbitTemplate.convertAndSend(dto);
    }
}
