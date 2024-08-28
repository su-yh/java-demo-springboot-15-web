package com.leomaster.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author suyh
 * @since 2023-11-26
 */
@Tag(name = "会员广告 - 广告内容管理")
@RestController
@RequestMapping(value = "/suyh/test")
@Slf4j
@Validated
public class SuyhTestController {
//    @RequestMapping(value = "/business/exception", method = RequestMethod.GET)
//    public void testBusinessException() {
//        throw ExceptionUtil.business(ErrorCodeConstants.PARAMETER_ERROR);
//    }
//
//    @RequestMapping(value = "/business/exception/params", method = RequestMethod.GET)
//    public void testBusinessExceptionParams() {
//        throw ExceptionUtil.business(ErrorCodeConstants.PARAMETER_ERROR_PARAM, "什么参数。");
//    }
//
//    @RequestMapping(value = "/system/exception", method = RequestMethod.GET)
//    public void testSystemException() {
//        throw ExceptionUtil.system(ErrorCodeConstants.PARAMETER_ERROR);
//    }

    @RequestMapping(value = "/response/string", method = RequestMethod.GET)
    public String testResponseString() {
        return "OK";
    }

    @RequestMapping(value = "/response/void", method = RequestMethod.GET)
    public void testResponseVoid() {
    }
}
