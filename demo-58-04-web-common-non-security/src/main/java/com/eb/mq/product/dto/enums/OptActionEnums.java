package com.eb.mq.product.dto.enums;

import lombok.Getter;

/**
 * @author suyh
 * @since 2024-09-05
 */
@Getter
public enum OptActionEnums {
    CREATE(1),
    UPDATE(2),
    ;

    private final int code;

    OptActionEnums(int code) {
        this.code = code;
    }
}
