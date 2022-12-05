package com.wbllwa.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户表
 * @author libw
 * @since 2022/12/5 16:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_user")
public class User
{

  /**
   * 主键
   */
  @TableId
  private Long id;

  /**
   * 账号
   */
  private String username;

  /**
   * 密码
   */
  private String password;

  /**
   * 年龄
   */
  private Integer age;

  /**
   * 邮箱
   */
  private String email;
}
