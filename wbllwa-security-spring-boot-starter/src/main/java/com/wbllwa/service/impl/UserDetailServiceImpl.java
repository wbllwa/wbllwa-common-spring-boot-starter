package com.wbllwa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wbllwa.domain.LoginUser;
import com.wbllwa.domain.User;
import com.wbllwa.mapper.UserMapper;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 实现从数据库查询用户信息
 * @author libw
 * @since 2022/12/1 15:22
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

  @Autowired
  private UserMapper userMapper;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(User::getUsername, username);
    User user = userMapper.selectOne(queryWrapper);

    if (Objects.isNull(user))
    {
      throw new UsernameNotFoundException("用户名或密码错误");
    }
    return new LoginUser(user);
  }
}
