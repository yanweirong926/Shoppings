package com.web.shopping;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.web.shopping.mapper")
public class AppPage {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(AppPage.class, args);
	}

}
