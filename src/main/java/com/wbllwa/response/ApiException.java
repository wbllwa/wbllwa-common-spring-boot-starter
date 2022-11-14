package com.wbllwa.response;

import lombok.Getter;

/**
 * 业务通用异常
 * @author libw
 * @since 2022/11/11 14:41
 */
public class ApiException extends RuntimeException
{
    /**
     * 错误码
     */
    @Getter
    private int errorCode;

    /**
     * 错误信息
     */
    @Getter
    private String errorMassage;

    public ApiException(int errorCode, String errorMassage)
    {
        super(errorMassage);
        this.errorCode = errorCode;
        this.errorMassage = errorMassage;
    }

    public ApiException(Throwable cause, int errorCode, String errorMassage)
    {
        super(errorMassage, cause);
        this.errorCode = errorCode;
        this.errorMassage = errorMassage;
    }
}
