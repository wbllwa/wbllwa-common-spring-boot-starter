package com.wbllwa.version;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * Api版本控制实现类
 * 默认通过配置文件控制版本，支持通过注解控制版本
 * @author libw
 * @since 2022/11/11 16:29
 */
@Slf4j
public class ApiVersionHandlerMapping extends RequestMappingHandlerMapping
{
    @Autowired
    private ApiVersionProperties apiVersionProperties;

    @Override
    protected boolean isHandler(Class<?> beanType)
    {
        return AnnotatedElementUtils.hasAnnotation(beanType, Controller.class)
                && !beanType.equals(BasicErrorController.class);
    }

    @Override
    protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping)
    {
        String[] urlPatterns = getApiVersionPatterns(method);

        RequestMappingInfo.BuilderConfiguration configuration = super.getBuilderConfiguration();
        RequestMappingInfo apiMappingInfo = RequestMappingInfo.paths(urlPatterns).options(configuration).build();
        RequestMappingInfo newMappingInfo = apiMappingInfo.combine(mapping);
        log.info("apiVersion: {}", newMappingInfo.getPatternValues());
        super.registerHandlerMethod(handler, method, newMappingInfo);
    }

    /**
     * 优先级：方法级别注解设置 > 类级别注解设置 > 全局版本设置
     * @param method
     * @return
     */
    private String[] getApiVersionPatterns(Method method)
    {
        Class<?> controllerClass = method.getDeclaringClass();
        String[] apiVersionPatterns;

        // 类上的注解
        ApiVersion apiVersion = AnnotationUtils.findAnnotation(controllerClass, ApiVersion.class);
        // 方法上的注解
        ApiVersion methodApiVersion = AnnotationUtils.findAnnotation(method, ApiVersion.class);
        // 优先使用方法上的注解
        if (methodApiVersion != null)
        {
            apiVersion = methodApiVersion;
        }

        if (apiVersion == null)
        {
            String global = apiVersionProperties.getGlobal();
            apiVersionPatterns = StringUtils.isNotEmpty(global) ? new String[]{global} : new String[0];
        }
        else
        {
            apiVersionPatterns = apiVersion.value();
        }

        return apiVersionPatterns;
    }
}
