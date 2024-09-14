package com.eb.rouyi.excel.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author suyh
 * @since 2024-09-04
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface ExcelEnumMessageCategory {
    /**
     * 在国际化中的枚举分类，不同枚举不可重复，对应的枚举的每一个值都要在 messages.properties 文件中配置
     */
    String value();
}
