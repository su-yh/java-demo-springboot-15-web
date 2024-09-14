package com.suyh5804.mvc.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * @author suyh
 * @since 2024-09-13
 */
@ControllerAdvice
@Slf4j
public class StringTrimmerControllerAdvice {

    /**
     * 将请求参数的字符串，两端的空格处理掉。
     */
    @InitBinder
    public void trimBinder(WebDataBinder binder) {
        // 仅适用于 @ModelAttribute 和 @RequestParam 绑定
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
