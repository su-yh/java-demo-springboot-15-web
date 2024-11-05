package com.eb.json;

import com.eb.json.deserializer.BizStringDeserializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Component;

@Component
public class PackMapperCustomizer implements Jackson2ObjectMapperBuilderCustomizer {
    @Override
    public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
        // suyh - 还没测试呢，但感觉应该是没问题的。处理Long 类型，序列化的时候处理成字符串
        jacksonObjectMapperBuilder.serializerByType(Long.class, ToStringSerializer.instance);
        jacksonObjectMapperBuilder.serializerByType(Long.TYPE, ToStringSerializer.instance);
        jacksonObjectMapperBuilder.deserializerByType(String.class, BizStringDeserializer.instance);
    }
}
