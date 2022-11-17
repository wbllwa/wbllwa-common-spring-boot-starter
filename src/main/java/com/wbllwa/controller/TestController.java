package com.wbllwa.controller;

import com.wbllwa.securefield.SecureField;
import com.wbllwa.response.ApiException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

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
//    @ApiVersion("v1")
    @GetMapping
    public Person test(@RequestBody Person person)
    {
        if (Objects.nonNull(person))
        {
            return person;
        }
        else
        {
            throw new ApiException(2001, "失败了");
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Person{
        @SecureField(type = SecureField.Type.REQUEST)
        private String name;

        @SecureField
        private String age;
    }
}
