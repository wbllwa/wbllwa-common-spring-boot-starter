package com.wbllwa.version;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * API版本控制配置类
 * @author libw
 * @since 2022/11/14 10:54
 */

@Data
@Component
@ConfigurationProperties(prefix = "api.version")
public class ApiVersionProperties
{
    /**
     * 全局版本
     */
    private String global;
}
