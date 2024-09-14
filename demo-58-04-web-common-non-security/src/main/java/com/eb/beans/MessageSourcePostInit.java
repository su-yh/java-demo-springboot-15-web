package com.eb.beans;

import com.eb.rouyi.excel.handler.ExcelEnumValueHandler;
import com.eb.rouyi.excel.poi.RuoyiExcelUtil;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * @author suyh
 * @since 2024-09-04
 */
@Component
public class MessageSourcePostInit implements MessageSourceAware {

    @Override
    public void setMessageSource(@NonNull MessageSource messageSource) {
        ExcelEnumValueHandler.MESSAGE_SOURCE = messageSource;
        RuoyiExcelUtil.MESSAGE_SOURCE = messageSource;
    }
}
