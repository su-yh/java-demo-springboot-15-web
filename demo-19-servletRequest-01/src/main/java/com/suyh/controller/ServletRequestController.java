package com.suyh.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@Api("servlet-request-controller")
public class ServletRequestController {

    @Resource
    private ServletRequestService service;

    @ApiOperation(value = "getInfo")
    @RequestMapping(value = "/get/info", method = RequestMethod.GET)
    public String getInfo() {
        service.temp();

        // 获取请求上下文，从中提取出header 中的token 数据
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        assert servletRequestAttributes != null;
        HttpServletRequest request = servletRequestAttributes.getRequest();

        String tokenValue = request.getHeader("token");
        log.info("ServletRequestController#getInfo(), current thread: {}, tokenValue: {}", Thread.currentThread().getName(), tokenValue);

        return tokenValue;
    }
}
