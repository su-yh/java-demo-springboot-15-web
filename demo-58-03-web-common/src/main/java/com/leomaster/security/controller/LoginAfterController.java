package com.leomaster.security.controller;

import com.leomaster.mvc.response.annotation.DisableControllerResponseAdvice;
import com.leomaster.security.service.PlatformUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证后置处理器
 *
 * @author suyh
 * @since 2024-03-08
 */
@RestController
@RequestMapping("/login/after")
@RequiredArgsConstructor
public class LoginAfterController {

    // suyh - 这里的问题主要是没有Authentication，有空的时候看下 SavedRequestAwareAuthenticationSuccessHandler  是怎么把这些参数补充上的
    // 不过这里保留了登录时的原始参数。
    @DisableControllerResponseAdvice
    @RequestMapping(value = "/successful/username/password", method = RequestMethod.POST)
    public String loginSuccessfulByUsernamePassword(String username, String password, Integer code) {
        // 走到这里就表示用户名密码验证通过了，接下来就可以做后续处理了。
        // 这里就可以处理验证码什么的。
        System.out.println("username: " + username);
        System.out.println("password: " + password);

        Map<String, Object> map = new HashMap<>();
        map.put("username", username);
        return PlatformUserDetailsService.createToken(map, username);
    }

    // 如果登录失败，一般是用户名或者密码错误。
    // 在这里实现，可以返回给前端自定义的一些数据信息，或者提示。
    @DisableControllerResponseAdvice
    @RequestMapping(value = "/failure/username/password", method = RequestMethod.POST)
    public String loginFailureByUsernamePassword(@RequestAttribute("msg") String msg) {
        // suyh - 在这里抛出业务异常。
        return msg;
    }
}
