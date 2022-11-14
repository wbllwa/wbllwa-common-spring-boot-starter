package com.wbllwa.response;

import java.lang.annotation.*;

/**
 * 增加此注解API响应不进行包装
 * @author libw
 * @since 2022/11/11 15:27
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoApiResponse
{
}
