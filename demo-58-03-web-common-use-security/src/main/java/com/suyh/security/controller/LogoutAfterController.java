package com.suyh.security.controller;

import com.suyh.mvc.vo.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author suyh
 * @since 2024-09-02
 */
@RestController
@RequestMapping("/logout/after")
@RequiredArgsConstructor
public class LogoutAfterController {
    @RequestMapping(value = "/successful", method = {RequestMethod.POST})
    @Deprecated
    public ResponseResult<String> loginSuccessful(Authentication authentication) {
        return ResponseResult.ofSuccess();
    }
}
