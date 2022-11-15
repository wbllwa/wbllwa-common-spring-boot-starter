package com.wbllwa.minio;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Minio配置类
 * @author libw
 * @since 2022/10/11 11:31
 */
@ConfigurationProperties(prefix = "minio")
@Data
public class MinioProperties
{
    /**
     * endpoint
     */
    private String endpoint;

    /**
     * accessKey
     */
    private String accessKey;

    /**
     * secretKey
     */
    private String secretKey;

    /**
     * bucket
     */
    private String bucket;
}
