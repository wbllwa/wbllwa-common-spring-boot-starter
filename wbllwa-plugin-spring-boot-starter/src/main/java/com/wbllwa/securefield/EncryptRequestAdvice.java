package com.wbllwa.securefield;

import cn.hutool.core.annotation.AnnotationUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wbllwa.response.ApiException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * 解密请求值
 * @author libw
 * @since 2022/11/16 11:07
 */
@ConditionalOnProperty(prefix = "secure.field", name = "enabled", havingValue = "true", matchIfMissing = false)
@ControllerAdvice
public class EncryptRequestAdvice implements RequestBodyAdvice
{
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecureBodyProperties secureBodyProperties;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType)
    {
        return true;
    }

    @SneakyThrows
    @Override
    public HttpInputMessage beforeBodyRead(
            HttpInputMessage inputMessage,
            MethodParameter parameter,
            Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType
    )
    {
        String typeName = targetType.getTypeName();
        Class<?> aClass = Class.forName(typeName);
        Object body = objectMapper.readValue(inputMessage.getBody(), aClass);
        Object newBody = this.requestFieldsDecrypt(body, aClass);
        byte[] bytes = objectMapper.writeValueAsBytes(newBody);
        return new HttpInputMessage()
        {
            @Override
            public HttpHeaders getHeaders()
            {
                return inputMessage.getHeaders();
            }

            @Override
            public InputStream getBody()
            {
                return new ByteArrayInputStream(bytes);
            }
        };
    }

    @Override
    public Object afterBodyRead(
            Object body,
            HttpInputMessage inputMessage,
            MethodParameter parameter,
            Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType
    )
    {
        return body;
    }

    @Override
    public Object handleEmptyBody(
            Object body,
            HttpInputMessage inputMessage,
            MethodParameter parameter,
            Type targetType,
            Class<? extends HttpMessageConverter<?>> converterType)
    {
        return body;
    }

    /**
     * 对body中带注解的字段进行解密
     * @param body
     * @param clazz
     * @return
     */
    public Object requestFieldsDecrypt(Object body, Class<?> clazz)
    {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields)
        {
            SecureField needSecure = AnnotationUtil.getAnnotation(field, SecureField.class);
            if (needSecure != null
                    && (needSecure.type().equals(SecureField.Type.REQUEST)
                    || needSecure.type().equals(SecureField.Type.ALL)))
            {
                Object fieldValue = ReflectUtil.getFieldValue(body, field);
                if (fieldValue != null)
                {
                    if (fieldValue instanceof String)
                    {
                        String encryptResult = rsaDecryptData((String)fieldValue);
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
    private String rsaDecryptData(String data)
    {
        RSA rsa = SecureUtil.rsa(secureBodyProperties.getPrivateKey(), secureBodyProperties.getPublicKey());
        try
        {
            return rsa.decryptStr(data, KeyType.PublicKey);
        }
        catch (Exception e)
        {
            throw new ApiException(e, -1, "安全字段 rsa 解密失败 ras encrypt");
        }
    }
}
