package com.wbllwa;

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
    user.setId("111");
    user.setUsername("libw");
    loginUser.setUser(user);
    String token = jwtUtil.generateToken(loginUser);
    System.out.println(token);

    System.out.println(jwtUtil.getUserIdFromToken(token));
  }
}
