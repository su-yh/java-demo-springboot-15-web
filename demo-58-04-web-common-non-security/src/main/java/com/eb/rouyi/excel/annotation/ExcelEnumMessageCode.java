package com.eb.rouyi.excel.annotation;

import com.baomidou.mybatisplus.annotation.EnumValue;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 参考自： {@link EnumValue}
 *
 * @author suyh
 * @since 2024-09-04
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface ExcelEnumMessageCode {
}
