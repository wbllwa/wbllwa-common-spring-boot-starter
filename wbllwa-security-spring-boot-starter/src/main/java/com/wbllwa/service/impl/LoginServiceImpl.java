package com.wbllwa.service.impl;

import com.wbllwa.contants.Contant;
import com.wbllwa.domain.LoginRequest;
import com.wbllwa.domain.LoginUser;
import com.wbllwa.domain.User;
import com.wbllwa.mapper.UserMapper;
import com.wbllwa.response.ApiException;
import com.wbllwa.service.LoginService;
import com.wbllwa.service.RedisService;
import com.wbllwa.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void register(LoginUser loginUser)
    {
        User user = loginUser.getUser();

        if (userMapper.existsUser(user))
        {
            throw new ApiException(-1, "用户名已存在，请重新注册!");
        }

        String savePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(savePassword);
        userMapper.insert(user);
    }

    @Override
    public Map<String, String> login(LoginRequest loginRequest)
    {
        UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken
                .unauthenticated(loginRequest.getUsername(), loginRequest.getPassword());
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
        redisService.set(Contant.LOGIN_KEY + loginUser.getUser().getId(), jwt);

        Map<String, String> result = new HashMap<>(1);
        result.put(JwtUtil.HEADER_KEY, JwtUtil.BEARER + jwt);
        return result;
    }

    @Override
    public void logout(HttpServletRequest request)
    {
        String token = jwtUtil.getToken(request);
        if (token == null)
        {
            throw new ApiException(-1, "用户未登陆无法登出");
        }

        Long userId = jwtUtil.getUserIdFromToken(token);
        redisService.remove(Contant.LOGIN_KEY + userId);
    }
}
