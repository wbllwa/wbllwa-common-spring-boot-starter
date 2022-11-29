package com.wbllwa.minio;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Minio自动配置
 * @author libw
 * @since 2022/10/11 10:51
 */
@Configuration
@ConditionalOnClass({MinioClient.class})
@ConditionalOnProperty(prefix = "minio", name = "enabled", havingValue = "true", matchIfMissing = false)
@EnableConfigurationProperties(MinioProperties.class)
public class MinioAutoConfiguration
{
    @Autowired
    private MinioProperties minioProperties;

    @ConditionalOnMissingBean
    @Bean
    public MinioClient minioClient()
    {
        return MinioClient.builder()
                .endpoint(minioProperties.getEndpoint())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                 .build();
    }

    @ConditionalOnClass({MinioClient.class})
    @ConditionalOnMissingBean
    @Bean
    public MinioService minioService()
    {
        return new MinioServiceImpl();
    }
}
