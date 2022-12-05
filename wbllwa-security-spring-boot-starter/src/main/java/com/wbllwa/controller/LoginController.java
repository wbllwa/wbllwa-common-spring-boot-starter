package com.wbllwa.controller;

import com.wbllwa.domain.LoginRequest;
import com.wbllwa.domain.LoginUser;
import com.wbllwa.service.LoginService;
import io.swagger.annotations.Api;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author libw
 * @since 2022/11/22 16:13
 */
@Api("登录接口")
@RestController
@RequestMapping("user")
public class LoginController
{
    @Autowired
    private LoginService loginService;

    @PostMapping("register")
    public void register(@RequestBody LoginUser loginUser)
    {
        loginService.register(loginUser);
    }

    @PostMapping("login")
    public Map<String, String> login(@RequestBody LoginRequest loginRequest)
    {
        return loginService.login(loginRequest);
    }

    @PostMapping("logout")
    public void logout(HttpServletRequest request)
    {
        loginService.logout(request);
    }
}
