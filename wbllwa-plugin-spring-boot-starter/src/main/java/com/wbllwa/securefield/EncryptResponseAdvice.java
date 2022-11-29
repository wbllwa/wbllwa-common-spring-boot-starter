package com.wbllwa.securefield;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.wbllwa.response.ApiException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Field;

/**
 * 加密响应返回值
 * @author libw
 * @since 2022/11/16 11:07
 */
@ConditionalOnProperty(prefix = "secure.field", name = "enabled", havingValue = "true", matchIfMissing = false)
@ControllerAdvice
public class EncryptResponseAdvice implements ResponseBodyAdvice<Object>
{
    @Autowired
    private SecureBodyProperties secureBodyProperties;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType)
    {
        return true;
    }

    @SneakyThrows
    @Override
    public Object beforeBodyWrite(
            Object body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response
    )
    {
        // 请求响应返回值类对象
        Class<?> returnTypeClazz = returnType.getMethod().getReturnType();
        return this.responseFieldsEncrypt(body, returnTypeClazz);
    }

    /**
     * 对body中带注解的字段进行加密
     * @param body
     * @param clazz
     * @return
     */
    private Object responseFieldsEncrypt(Object body, Class<?> clazz)
    {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields)
        {
            SecureField needSecure = AnnotationUtil.getAnnotation(field, SecureField.class);
            if (needSecure != null
                    && (needSecure.type().equals(SecureField.Type.RESPONSE)
                    || needSecure.type().equals(SecureField.Type.ALL)))
            {
                Object fieldValue = ReflectUtil.getFieldValue(body, field);
                if (fieldValue != null)
                {
                    if (fieldValue instanceof String)
                    {
                        String encryptResult = rsaEncryptData((String)fieldValue);
                        ReflectUtil.setFieldValue(body, field, encryptResult);
                    }
                }
            }
        }
        return body;
    }

    /**
     * 使用RSA非对称加密
     * @param data
     * @return
     */
    private String rsaEncryptData(String data)
    {
        RSA rsa = SecureUtil.rsa(secureBodyProperties.getPrivateKey(), secureBodyProperties.getPublicKey());
        try
        {
            return rsa.encryptBase64(data, KeyType.PrivateKey);
        }
        catch (Exception e)
        {
            throw new ApiException(e, -1, "安全字段 rsa 加密失败");
        }
    }
}
