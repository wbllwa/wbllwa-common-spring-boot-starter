package com.wbllwa.controller;

import com.wbllwa.response.ApiException;
import com.wbllwa.securefield.SecureField;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试controller
 * @author libw
 * @since 2022/11/11 14:45
 */
//@ApiVersion("v2")
@Api(tags = "测试controller")
@Slf4j
@RestController
@RequestMapping("test")
public class TestController
{
//    @ApiVersion("v1")
    @ApiOperation("测试接口")
    @PostMapping
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

    @ApiModel("测试人对象")
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class Person{

        @ApiModelProperty(value = "测试人名称")
        @SecureField(type = SecureField.Type.REQUEST)
        private String name;

        @ApiModelProperty(value = "测试人年龄")
        @SecureField
        private String age;
    }
}
