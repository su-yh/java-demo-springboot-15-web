package com.suyh5804.business.controller;

import com.suyh5804.business.dto.UserCreateDto;
import com.suyh5804.business.dto.UserLoginDto;
import com.suyh5804.business.service.UserService;
import com.suyh5804.constant.ErrorCodeConstants;
import com.suyh5804.mvc.authentication.annotation.Permit;
import com.suyh5804.mvc.exception.ExceptionUtil;
import com.suyh5804.mvc.authentication.CurrLoginUser;
import com.suyh5804.mvc.authentication.LoginUser;
import com.suyh5804.mvc.response.annotation.WrapperResponseAdvice;
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
