package com.wbllwa;

import com.wbllwa.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ApplicationTest {

  @Autowired
  private UserMapper userMapper;

  @Test
  public void testUserMapper() {
    System.out.println(userMapper.selectList(null));
  }
}
