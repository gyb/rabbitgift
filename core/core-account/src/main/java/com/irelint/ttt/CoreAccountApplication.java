package com.irelint.ttt;

import org.mvnsearch.spring.boot.dubbo.EnableDubboConfiguration;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.irelint.ttt.util.Constants;

@SpringBootApplication
@EnableDubboConfiguration("com.irelint.ttt")
public class CoreAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreAccountApplication.class, args);
	}
	
	@Bean
	DirectExchange exchange() {
		return new DirectExchange(Constants.EXCHANGE_NAME);
	}
	
	@Bean
	Queue userCreated() {
		return new Queue(Constants.QUEUE_USERCREATED_ACCOUNT);
	}
	
	@Bean
	Binding bindingUserCreated(DirectExchange exchange, @Qualifier("userCreated") Queue queue) {
		return BindingBuilder.bind(queue).to(exchange).with(Constants.EVENT_USERCREATED);
	}
	
	@Bean
	Queue toPayOrder() {
		return new Queue("toPayOrder-account");
	}
	
	@Bean
	Binding bindingToPayOrder(DirectExchange exchange, @Qualifier("toPayOrder") Queue queue) {
		return BindingBuilder.bind(queue).to(exchange).with(Constants.EVENT_TOPAYORDER);
	}
	
	@Bean
	Queue toRefundOrder() {
		return new Queue("toRefundOrder-account");
	}
	
	@Bean
	Binding bindingToRefundOrder(DirectExchange exchange, @Qualifier("toRefundOrder") Queue queue) {
		return BindingBuilder.bind(queue).to(exchange).with(Constants.EVENT_TOREFUNDORDER);
	}
	
	@Bean
	Queue orderReceived() {
		return new Queue("orderReceived-account");
	}
	
	@Bean
	Binding bindingOrderReceived(DirectExchange exchange, @Qualifier("orderReceived") Queue queue) {
		return BindingBuilder.bind(queue).to(exchange).with(Constants.EVENT_ORDERRECEIVED);
	}
}

