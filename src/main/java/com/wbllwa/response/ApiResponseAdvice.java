package com.wbllwa.response;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 响应体包装 （外部系统强制使用）
 * @RestControllerAdvice + @ExceptionHandler 处理全局异常
 * ResponseBodyAdvice 对controller返回响应进行包装
 * @author libw
 * @since 2022/11/11 15:00
 */
@RestControllerAdvice
@Slf4j
public class ApiResponseAdvice implements ResponseBodyAdvice<Object>
{

    /**
     * 全局异常处理
     * @param ex
     * @return
     */
    @ExceptionHandler
    public ApiResponse handlerException(Exception ex)
    {
        String errorMessage = "系统内部出错";
        log.error(errorMessage, ex);
        return ApiResponse.failure(-1, errorMessage);
    }

    /**
     * API业务异常处理
     * @param request
     * @param ex
     * @return
     */
    @ExceptionHandler
    public ApiResponse handleApiException(HttpServletRequest request, ApiException ex)
    {
        log.error(StrUtil.format("API 异常：{}", request.getRequestURL().toString()), ex);
        return ApiResponse.failure(ex.getErrorCode(), ex.getErrorMassage());
    }

    /**
     * 仅当方法或类没有标记@NoAPIResponse才自动包装
     * @param returnType
     * @param converterType
     * @return
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType)
    {
        return returnType.getParameterType() != ApiResponse.class
                && AnnotationUtils.findAnnotation(returnType.getMethod(), NoApiResponse.class) == null
                && AnnotationUtils.findAnnotation(returnType.getDeclaringClass(), NoApiResponse.class) == null;
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response
    )
    {
        return ApiResponse.success(body);
    }
}
