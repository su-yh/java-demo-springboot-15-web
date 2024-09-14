package com.eb.business.dto;

import java.util.List;
import java.util.UUID;

/**
 * @author suyh
 * @since 2024-09-07
 */
public class UuidPlus {
    // 为前端补充唯一值
    public static <T extends UuidRspDto> void setUuidList(List<T> listDto) {
        if (listDto == null) {
            return;
        }

        for (T dto : listDto) {
            dto.setUuid(UUID.randomUUID().toString().replaceAll("-", ""));
        }
    }
}
