package com.wbllwa.controller;

import com.wbllwa.domain.LoginRequest;
import com.wbllwa.service.LoginService;
import io.swagger.annotations.Api;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("login")
    public Map<String, String> login(@RequestBody LoginRequest loginRequest)
    {
        return loginService.login(loginRequest);
    }
}
