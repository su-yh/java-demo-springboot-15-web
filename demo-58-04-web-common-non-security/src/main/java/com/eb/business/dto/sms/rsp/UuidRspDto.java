package com.eb.business.dto.sms.rsp;

/**
 * @author suyh
 * @since 2024-09-07
 */
public interface UuidRspDto<T> {
    String getUuid();

    T setUuid(String uuid);
}
