package com.wbllwa.version;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author libw
 * @since 2022/11/11 16:16
 */
@Configuration
public class WebRegistrationConfig implements WebMvcRegistrations
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
