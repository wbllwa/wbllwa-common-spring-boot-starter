package com.wbllwa.securefield;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 消息体加密配置
 * @author libw
 * @since 2022/11/16 14:58
 */
@Data
@ConfigurationProperties(prefix = "secure.field")
public class SecureBodyProperties
{
    /**
     * 非对称加密私钥
     */

    private String privateKey;

    /**
     * 非对称加密公钥
     */
    private String publicKey;

}
