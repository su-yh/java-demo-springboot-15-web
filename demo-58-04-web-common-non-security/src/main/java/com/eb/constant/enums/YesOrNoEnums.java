package com.eb.constant.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * @author suyh
 * @since 2024-09-03
 */
@Getter
public enum YesOrNoEnums {
    YES(1),
    NO(0),
    ;

    @EnumValue
    private final int code;

    YesOrNoEnums(int code) {
        this.code = code;
    }
}
