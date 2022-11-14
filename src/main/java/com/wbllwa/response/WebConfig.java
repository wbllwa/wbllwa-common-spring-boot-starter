package com.wbllwa.response;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author libw
 * @since 2022/11/11 16:16
 */
@Configuration
public class WebConfig implements WebMvcConfigurer
{

    /**
     * 配置消息处理器，防止string转换异常
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters)
    {
        converters.clear();
        converters.add(0, new MappingJackson2HttpMessageConverter());
    }
}
