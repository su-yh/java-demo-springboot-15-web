package com.eb.json;

import com.eb.json.deserializer.BizStringDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.NonNull;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author suyh
 * @since 2024-03-11
 * @deprecated 使用 {@link PackMapperCustomizer} 替代
 */
//@Component
@Slf4j
public class JsonPostProcessor implements BeanPostProcessor {
    private final AtomicInteger count = new AtomicInteger(0);

    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        if (ObjectMapper.class.isAssignableFrom(bean.getClass())) {
            log.info("init json utils.");
            assert count.incrementAndGet() == 1;

            ObjectMapper objectMapper = (ObjectMapper) bean;

            SimpleModule simpleModule = new SimpleModule();
            simpleModule.addSerializer(Long.class, ToStringSerializer.instance)
                    .addSerializer(Long.TYPE, ToStringSerializer.instance);
            simpleModule.addDeserializer(String.class, BizStringDeserializer.instance);
            objectMapper.registerModules(simpleModule);
        }

        return bean;
    }
}
