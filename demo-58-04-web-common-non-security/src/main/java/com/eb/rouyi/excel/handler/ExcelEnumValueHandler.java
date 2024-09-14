package com.eb.rouyi.excel.handler;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.eb.mp.typehandler.enlist.AbstractEnumListTypeHandler;
import com.eb.rouyi.excel.annotation.ExcelEnumMessageCategory;
import com.eb.rouyi.excel.annotation.ExcelEnumMessageCode;
import com.eb.rouyi.excel.poi.RuoyiExcelHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaClass;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.invoker.Invoker;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.MessageSource;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 需要注册，对应的枚举要添加注解：{@link ExcelEnumMessageCategory} 以及 {@link ExcelEnumMessageCode}
 * 然后再到messages.properties 配置对应的值。
 *
 * @author suyh
 * @since 2024-09-04
 */
@Slf4j
public class ExcelEnumValueHandler implements RuoyiExcelHandlerAdapter {
    public static final String MESSAGE_PREFIX = "excel.field.enum";
    public static MessageSource MESSAGE_SOURCE;

    private static final ReflectorFactory REFLECTOR_FACTORY = new DefaultReflectorFactory();
    private static final Map<String, String> TABLE_METHOD_OF_ENUM_TYPES = new ConcurrentHashMap<>();

    @Override
    public Object format(Object value, Locale locale, String[] args, Cell cell, Workbook wb) {
        if (value == null) {
            return null;
        }
        Class<?> enumClassType = value.getClass();
        if (!Enum.class.isAssignableFrom(enumClassType)) {
            log.error("NON {}, UNKNOWN CLASS: {}", Enum.class.getSimpleName(), value.getClass().getSimpleName());

            return null;
        }

        Enum<?> en = (Enum<?>) value;
        if (MESSAGE_SOURCE == null) {
            return en.name();
        }

        ExcelEnumMessageCategory excelEnumMessageCategory = en.getClass().getAnnotation(ExcelEnumMessageCategory.class);
        if (excelEnumMessageCategory == null) {
            return en.name();
        }

        String name = findExcelEnumMessageCodeFieldName(enumClassType).orElseThrow(
                () -> new IllegalArgumentException(
                        String.format("Could not find @%s in Class: %s.",
                                ExcelEnumMessageCode.class.getSimpleName(), enumClassType.getName())));

        MetaClass metaClass = MetaClass.forClass(enumClassType, REFLECTOR_FACTORY);
        Invoker getInvoker = metaClass.getGetInvoker(name);
        // 国际化对应的code
        Object codeSuffix = getValue(getInvoker, en);
        String messageCode = String.format("%s.%s.%s", MESSAGE_PREFIX, excelEnumMessageCategory.value(), codeSuffix);
        return MESSAGE_SOURCE.getMessage(messageCode, null, en.name(), locale);
    }

    private Object getValue(Invoker getInvoker, Object e) {
        try {
            return getInvoker.invoke(e, new Object[0]);
        } catch (ReflectiveOperationException ex) {
            throw ExceptionUtils.mpe(ex);
        }
    }

    /**
     * 参考自：{@link AbstractEnumListTypeHandler#findEnumValueFieldName(Class)}
     *
     * 查找标记标记 {@link ExcelEnumMessageCode} 字段
     */
    public static Optional<String> findExcelEnumMessageCodeFieldName(Class<?> clazz) {
        if (clazz != null && clazz.isEnum()) {
            String className = clazz.getName();
            return Optional.ofNullable(CollectionUtils.computeIfAbsent(TABLE_METHOD_OF_ENUM_TYPES, className, key -> {
                Optional<Field> fieldOptional = findExcelEnumMessageCodeAnnotationField(clazz);
                return fieldOptional.map(Field::getName).orElse(null);
            }));
        }
        return Optional.empty();
    }

    private static Optional<Field> findExcelEnumMessageCodeAnnotationField(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields()).filter(field -> field.isAnnotationPresent(ExcelEnumMessageCode.class)).findFirst();
    }
}
