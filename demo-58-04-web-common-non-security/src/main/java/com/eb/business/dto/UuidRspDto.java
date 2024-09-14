package com.eb.business.dto;

import java.util.UUID;

/**
 * @author suyh
 * @since 2024-09-07
 */
public interface UuidRspDto {
    default String getUuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
