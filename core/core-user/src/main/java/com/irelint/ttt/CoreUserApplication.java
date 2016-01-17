package com.irelint.ttt;

import org.mvnsearch.spring.boot.dubbo.EnableDubboConfiguration;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.irelint.ttt.util.Constants;

@SpringBootApplication
@EnableDubboConfiguration("com.irelint.ttt")
public class CoreUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreUserApplication.class, args);
	}
	
	@Bean
	DirectExchange exchange() {
		return new DirectExchange(Constants.EXCHANGE_NAME);
	}
}
