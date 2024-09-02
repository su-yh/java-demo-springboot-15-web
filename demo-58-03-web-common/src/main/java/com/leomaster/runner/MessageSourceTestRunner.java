package com.leomaster.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Locale;

/**
 * TODO: suyh - 用来测试的。
 *
 * @author suyh
 * @since 2023-12-07
 */
@Component
@Slf4j
public class MessageSourceTestRunner implements ApplicationRunner {
    @Resource
    private MessageSource messageSource;

    @Override
    public void run(ApplicationArguments args) throws Exception {

//        String message = messageSource.getMessage("error.code.1015000", null, LocaleContextHolder.getLocale());
//        System.out.println(message);
//        message = messageSource.getMessage("error.code.1015000", null, Locale.SIMPLIFIED_CHINESE);
//        System.out.println(message);
//        message = messageSource.getMessage("error.code.1015000", null, Locale.ENGLISH);
//        System.out.println(message);
//        message = messageSource.getMessage("suyh.notExist", null, "没有配置", LocaleContextHolder.getLocale());
//        System.out.println(message);

        Locale locale = new Locale("mr", "IN");
        String  message = messageSource.getMessage("error.code.1015000", null, locale);
        System.out.println("印度语言：" + message);
    }
}
