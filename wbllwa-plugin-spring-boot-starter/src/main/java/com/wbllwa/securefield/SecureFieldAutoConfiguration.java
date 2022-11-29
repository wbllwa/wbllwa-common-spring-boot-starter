package com.wbllwa.securefield;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 安全自动自动配置类
 * @author libw
 * @since 2022/11/17 10:53
 */

@Configuration
@EnableConfigurationProperties(SecureBodyProperties.class)
public class SecureFieldAutoConfiguration
{

}
