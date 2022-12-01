package com.wbllwa.service;

/**
 * Redis服务
 * @author libw
 * @since 2022/12/1 15:09
 */
public interface RedisService
{
    /**
     * 存储数据
     * @param key
     * @param value
     */
    void set(String key, String value);

    /**
     * 获取数据
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 删除数据
     * @param key
     */
    void remove(String key);
}
