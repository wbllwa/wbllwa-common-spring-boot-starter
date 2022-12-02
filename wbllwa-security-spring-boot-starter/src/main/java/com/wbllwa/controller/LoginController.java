package com.wbllwa.controlller;

import com.wbllwa.domain.User;
import com.wbllwa.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
    public Map<String, String> login(@RequestBody User user)
    {
        return loginService.login(user);
    }
}
