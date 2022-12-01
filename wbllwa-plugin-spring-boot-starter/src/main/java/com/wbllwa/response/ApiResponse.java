package com.wbllwa.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 接口响应对象
 * @author libw
 * @since 2022/11/11 14:40
 */
@Data
@AllArgsConstructor
public class ApiResponse<T>
{
    /**
     * 返回结果
     */
    private T data;

    /**
     * 错误码
     */
    private int code;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 成功响应
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ApiResponse<T> success(T data)
    {
        return new ApiResponse<>(data, 0, "");
    }

    /**
     * 失败响应
     * @param code
     * @param message
     * @return
     */
    public static ApiResponse failure(int code, String message)
    {
        return new ApiResponse<>(null, code, message);
    }

    /**
     * 失败响应
     * @param message
     * @return
     */
    public static ApiResponse failure(String message)
    {
        return new ApiResponse<>(null, -1, message);
    }

}
