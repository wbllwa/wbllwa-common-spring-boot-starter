package com.wbllwa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class WbllwaCommonSpringBootStarterApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(WbllwaCommonSpringBootStarterApplication.class, args);
	}

}
