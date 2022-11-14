package com.wbllwa.controller;

import com.wbllwa.response.ApiException;
import com.wbllwa.version.ApiVersion;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 测试controller
 * @author libw
 * @since 2022/11/11 14:45
 */
//@ApiVersion("v2")
@Slf4j
@RestController
@RequestMapping("test")
public class TestController
{
    @ApiVersion("v1")
    @GetMapping(value = "{status}")
    public String test(@PathVariable String status)
    {
        if ("success".equalsIgnoreCase(status))
        {
            return "成功咯";
        }
        else
        {
            throw new ApiException(2001, "失败了");
        }
    }

    @GetMapping(value = "{test}")
    public String test2(@PathVariable String test)
    {
        if ("success".equalsIgnoreCase(test))
        {
            return "成功咯";
        }
        else
        {
            throw new ApiException(2001, "失败了");
        }
    }

    @GetMapping(value = "{test3}")
    public String test3(@PathVariable String test3)
    {
        if ("success".equalsIgnoreCase(test3))
        {
            return "成功咯";
        }
        else
        {
            throw new ApiException(2001, "失败了");
        }
    }
}
