package com.eb.event;

import com.eb.mq.product.dto.MqMessageProductDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.lang.NonNull;

/**
 * @author suyh
 * @since 2024-09-07
 */
@Getter
public class MqMessageEvent extends ApplicationEvent {
    private final MqMessageProductDto messageProductDto;

    public MqMessageEvent(@NonNull MqMessageProductDto messageProductDto) {
        super(messageProductDto);

        this.messageProductDto = messageProductDto;
    }
}
