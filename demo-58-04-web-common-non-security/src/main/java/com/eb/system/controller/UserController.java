package com.eb.system.controller;

import com.eb.business.dto.base.IdBody;
import com.eb.mvc.authentication.CurrLoginUser;
import com.eb.mvc.authentication.LoginUser;
import com.eb.mvc.authentication.annotation.Permit;
import com.eb.mvc.vo.ResponseResult;
import com.eb.system.dto.req.UserLoginReqDto;
import com.eb.system.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "用户")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserController {
    private final UserService userService;

    @Operation(summary = "用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @Permit(required = false)
    public ResponseResult<String> login(@RequestBody @Validated UserLoginReqDto userLogin) {
        String token = userService.login(
                userLogin.getUsername(), userLogin.getPassword(), userLogin.getCode());
        return ResponseResult.ofSuccess(token);
    }

    @Operation(summary = "用户登出")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @Permit(required = false)
    public ResponseResult<Boolean> logout() {
        return ResponseResult.ofSuccess();
    }

    @Operation(summary = "重置2FA：用户ID")
    @RequestMapping(value = "/reset/twoFactorAuthKey/byId", method = RequestMethod.POST)
    public ResponseResult<String> resetTwoFactorAuthKeyById(@RequestBody @Validated IdBody idBody) {
        String fa = userService.resetTwoFactorAuthKey(idBody.getId());
        return ResponseResult.ofSuccess(fa);
    }

    @Operation(summary = "重置2FA：当前登录用户")
    @RequestMapping(value = "/reset/twoFactorAuthKey/self", method = RequestMethod.POST)
    public ResponseResult<String> resetTwoFactorAuthKeySelf(
            @Parameter(hidden = true) @CurrLoginUser LoginUser loginUser) {
        String key = userService.resetTwoFactorAuthKey(loginUser.getId());
        return ResponseResult.ofSuccess(key);
    }
}
