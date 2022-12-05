package com.wbllwa;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wbllwa.domain.LoginUser;
import com.wbllwa.domain.User;
import com.wbllwa.mapper.UserMapper;
import com.wbllwa.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApplicationTest {

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private JwtUtil jwtUtil;

  @Test
  public void testUserMapper() {
    System.out.println(userMapper.selectList(null));
  }

  @Test
  public void testJwt() {
    LoginUser loginUser = new LoginUser();
    User user = new User();
    user.setId(111L);
    user.setUsername("libw");
    loginUser.setUser(user);
    String token = jwtUtil.generateToken(loginUser);
    System.out.println(token);

    System.out.println(jwtUtil.getUserIdFromToken(token));
  }

  @Test
  public void testSql()
  {
    User user = new User();
    user.setUsername("libw");
    LambdaQueryWrapper<User> queryWrapper = Wrappers.lambdaQuery();
    queryWrapper.eq(User::getUsername, user.getUsername());
    System.out.println(userMapper.exists(queryWrapper));
  }
}
