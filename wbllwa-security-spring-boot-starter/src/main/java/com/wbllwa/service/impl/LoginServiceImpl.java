package com.wbllwa.service.impl;

import com.wbllwa.domain.LoginUser;
import com.wbllwa.domain.User;
import com.wbllwa.response.ApiException;
import com.wbllwa.service.LoginService;
import com.wbllwa.service.RedisService;
import com.wbllwa.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author libw
 * @since 2022/12/1 10:52
 */
@Service
public class LoginServiceImpl implements LoginService
{
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisService redisService;

    @Override
    public Map<String, String> login(User user)
    {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        // 登陆认证
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        // 认证失败
        if (Objects.isNull(authenticate))
        {
            throw new ApiException(-1, "登陆失败，检查用户名密码");
        }

        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();

        // 生成jwt
        String jwt = jwtUtil.generateToken(loginUser);

        // 把jwt放入redis
        redisService.set("login:" + loginUser.getUser().getId(), jwt);

        Map<String, String> result = new HashMap<>(1);
        result.put(JwtUtil.HEADER_KEY, JwtUtil.BEARER + jwt);
        return result;
    }
}
