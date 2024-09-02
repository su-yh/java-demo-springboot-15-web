package com.leomaster.business.controller;

import com.leomaster.business.dto.UserCreateDto;
import com.leomaster.business.dto.UserLoginDto;
import com.leomaster.business.service.UserService;
import com.leomaster.constant.ErrorCodeConstants;
import com.leomaster.mvc.authentication.annotation.Permit;
import com.leomaster.mvc.exception.ExceptionUtil;
import com.leomaster.mvc.authentication.CurrLoginUser;
import com.leomaster.mvc.authentication.LoginUser;
import com.leomaster.mvc.response.annotation.WrapperResponseAdvice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author suyh
 * @since 2024-09-02
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserController {
    private final UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @WrapperResponseAdvice
    @Permit(required = false)
    public String login(@RequestBody UserLoginDto userLogin) {
        return userService.login(userLogin.getUsername(), userLogin.getPassword());
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @WrapperResponseAdvice
    @Permit(required = false)
    public void logout() {
    }

    /**
     * 创建用户
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @WrapperResponseAdvice
    public Long createUser(@RequestBody UserCreateDto dto) {
        return userService.createUser(dto);
    }

    /**
     * TODO: suyh - 用于测试的
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @WrapperResponseAdvice
    public String list(@CurrLoginUser(required = true) LoginUser loginUser) {
        return "aaaa";
    }

    // TODO: suyh - 用于测试的
    @RequestMapping(value = "/suyh-test", method = RequestMethod.GET)
    public String suyhTest() {
        throw ExceptionUtil.business(ErrorCodeConstants.SERVICE_ERROR);
    }
}
