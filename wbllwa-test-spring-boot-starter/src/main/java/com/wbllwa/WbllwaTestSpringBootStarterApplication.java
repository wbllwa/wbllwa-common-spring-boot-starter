package com.wbllwa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.wbllwa.mapper")
@SpringBootApplication
public class WbllwaTestSpringBootStarterApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(WbllwaTestSpringBootStarterApplication.class, args);
	}

}
