package com.irelint.ttt;

import org.mvnsearch.spring.boot.dubbo.EnableDubboConfiguration;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.irelint.ttt.util.Constants;

@SpringBootApplication
@EnableDubboConfiguration("com.irelint.ttt")
public class CoreProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreProductApplication.class, args);
	}
	
	@Bean
	DirectExchange exchange() {
		return new DirectExchange(Constants.EXCHANGE_NAME);
	}
	
	@Bean
	Queue queue() {
		return new Queue(Constants.QUEUE_GOODSRATED_GOODS);
	}
	
	@Bean
	Binding binding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(Constants.EVENT_GOODSRATED);
	}
}
