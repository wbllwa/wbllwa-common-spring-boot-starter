package com.wbllwa.minio;

/**
 * Minio运行时异常
 * @author libw
 * @since 2022/11/15 10:55
 */
public class MinioException extends RuntimeException
{
    public MinioException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
