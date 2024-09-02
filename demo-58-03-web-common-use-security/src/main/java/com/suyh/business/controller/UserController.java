package com.suyh.business.controller;

import com.suyh.business.dto.UserCreateDto;
import com.suyh.business.dto.UserLoginDto;
import com.suyh.business.service.UserService;
import com.suyh.constant.ErrorCodeConstants;
import com.suyh.mvc.exception.ExceptionUtil;
import com.suyh.mvc.authentication.CurrLoginUser;
import com.suyh.mvc.authentication.LoginUser;
import com.suyh.mvc.response.annotation.WrapperResponseAdvice;
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
    public String login(@RequestBody UserLoginDto userLogin) {
        return userService.login(userLogin.getUsername(), userLogin.getPassword());
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
