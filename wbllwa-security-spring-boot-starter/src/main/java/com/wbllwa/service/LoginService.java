package com.wbllwa.service;

import com.wbllwa.domain.User;

import java.util.Map;

/**
 * 登陆服务
 * @author libw
 * @since 2022/12/1 10:51
 */
public interface LoginService
{
    /**
     * 用户登陆
     * @param user 用户名密码
     * @return token
     */
    Map<String, String> login(User user);
}
