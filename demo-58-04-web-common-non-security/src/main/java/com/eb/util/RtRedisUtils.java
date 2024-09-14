package com.eb.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author suyh
 * @since 2024-04-22
 */
@Slf4j
public class RtRedisUtils {

    public static String getRedisValue(StringRedisTemplate redisTemplate, String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.warn("redis read failed, key: {}", key, e);
            return null;
        }
    }

    public static void setRedisValue(
            StringRedisTemplate redisTemplate, String key, String value, long timeout, TimeUnit unit) {
        try {
            redisTemplate.opsForValue().set(key, value, timeout, unit);
        } catch (Exception e) {
            log.warn("redis write failed, key: {}, value: {}", key, value, e);
        }
    }

}
