package com.wbllwa.service.impl;

import com.wbllwa.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Redis服务实现类
 * @author libw
 * @since 2022/12/1 15:09
 */
@Service
public class RedisServiceImpl implements RedisService
{
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void set(String key, String value)
    {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String get(String key)
    {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public void remove(String key)
    {
        stringRedisTemplate.delete(key);
    }
}
