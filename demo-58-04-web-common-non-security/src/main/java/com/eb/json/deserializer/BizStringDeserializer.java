package com.eb.json.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;

import java.io.IOException;

/**
 * @author suyh
 * @since 2024-09-13
 */
public class BizStringDeserializer extends StringDeserializer {
    public final static BizStringDeserializer instance = new BizStringDeserializer();

    /**
     * 反序列化，处理空白字符串
     */
    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        return super.deserialize(p,ctxt).trim();
    }
}
