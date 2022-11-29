package com.wbllwa.version;

import java.lang.annotation.*;

/**
 * API版本注解 用于@RestController @RequestMapping 添加版本注解
 * @author libw
 * @since 2022/11/11 16:28
 */
@Target({ ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiVersion
{
    String[] value();
}
