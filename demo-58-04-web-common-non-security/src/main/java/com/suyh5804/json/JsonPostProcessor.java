package com.suyh5804.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.NumberSerializer;
import com.suyh5804.json.deserializer.BizStringDeserializer;
import com.suyh5804.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author suyh
 * @since 2024-03-11
 */
@Component
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
            simpleModule.addSerializer(Long.class, NumberSerializer.instance)
                    .addSerializer(Long.TYPE, NumberSerializer.instance);
            // 处理反序列化时，字符串两端的空白字符。针对RequestBody 注解对应的请求参数特别有用。
            simpleModule.addDeserializer(String.class, BizStringDeserializer.instance);
            objectMapper.registerModules(simpleModule);

            // 同步 JsonUtils 与web 中使用的jackson 一致
            JsonUtils.initMapper(objectMapper);
        }
        return bean;
    }
}
