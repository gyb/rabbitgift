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
public class CoreInventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreInventoryApplication.class, args);
	}
	
	@Bean
	DirectExchange exchange() {
		return new DirectExchange(Constants.EXCHANGE_NAME);
	}
	
	@Bean
	Queue goodsCreated() {
		return new Queue(Constants.QUEUE_GOODSCREATED_INVENTORY);
	}
	
	@Bean
	Binding bindingGoodsCreated(@Qualifier("goodsCreated") Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(Constants.EVENT_GOODSCREATED);
	}
	
	@Bean
	Queue orderCreated() {
		return new Queue(Constants.QUEUE_ORDERCREATED_INVENTORY);
	}
	
	@Bean
	Binding bindingOrderCreated(@Qualifier("orderCreated") Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(Constants.EVENT_ORDERCREATED);
	}
	
	@Bean
	Queue orderCanceled() {
		return new Queue(Constants.QUEUE_ORDERCANCELED_INVENTORY);
	}
	
	@Bean
	Binding bindingOrderCanceled(@Qualifier("orderCanceled") Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(Constants.EVENT_ORDERCANCELED);
	}
}

