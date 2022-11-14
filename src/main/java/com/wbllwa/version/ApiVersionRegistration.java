package com.wbllwa.version;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author libw
 * @since 2022/11/11 16:16
 */
@ConditionalOnProperty(prefix = "api.version", name = "enabled", havingValue = "true", matchIfMissing = false)
@EnableConfigurationProperties(ApiVersionProperties.class)
public class ApiVersionRegistration implements WebMvcRegistrations
{
    /**
     * 加载自定义ApiVersionHandlerMapping
     * @return
     */
    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping()
    {
        return new ApiVersionHandlerMapping();
    }
}
