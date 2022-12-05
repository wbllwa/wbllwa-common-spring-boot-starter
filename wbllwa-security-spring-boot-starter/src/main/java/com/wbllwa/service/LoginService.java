package com.wbllwa.service;

import com.wbllwa.domain.LoginRequest;
import com.wbllwa.domain.LoginUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 登陆服务
 * @author libw
 * @since 2022/12/1 10:51
 */
public interface LoginService
{

    /**
     * 用户注册
     * @param loginUser
     */
    void register(LoginUser loginUser);
    /**
     * 用户登陆
     * @param loginRequest 用户名密码
     * @return token
     */
    Map<String, String> login(LoginRequest loginRequest);

    /**
     * 用户注销
     * @return
     */
    void logout(HttpServletRequest request);

}
