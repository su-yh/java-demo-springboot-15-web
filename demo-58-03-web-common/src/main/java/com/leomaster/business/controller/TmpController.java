package com.leomaster.business.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author suyh
 * @since 2024-08-28
 */
@RestController
@RequestMapping("/suyh/tmp")
public class TmpController {

    // 登录成功之后跳转的接口
    @GetMapping("/index")
    public String index() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return "hello index";
    }
}
