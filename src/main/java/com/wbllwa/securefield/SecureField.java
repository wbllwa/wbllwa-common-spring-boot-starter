package com.wbllwa.securefield;

import java.lang.annotation.*;

/**
 * 对字段进行安全加解密
 * @author libw
 * @since 2022/11/16 11:38
 */
@Target({ ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SecureField
{
    /**
     * 默认对请求解密 响应加密
     * @return
     */
    Type type() default Type.ALL;

    /**
     * 字段加密类型
     */
    enum Type{
        /**
         * 对请求解密 响应加密
         */
        ALL,
        /**
         * 只对请求解密
         */
        REQUEST,

        /**
         * 只对响应加密
         */
        RESPONSE
    }
}
