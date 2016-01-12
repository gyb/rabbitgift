package com.irelint.ttt;

import org.mvnsearch.spring.boot.dubbo.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubboConfiguration("com.irelint.ttt")
public class CoreInventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreInventoryApplication.class, args);
	}
}

