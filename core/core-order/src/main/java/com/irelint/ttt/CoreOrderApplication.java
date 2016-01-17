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
public class CoreOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreOrderApplication.class, args);
	}
	
	@Bean
	DirectExchange exchange() {
		return new DirectExchange(Constants.EXCHANGE_NAME);
	}
	
	@Bean
	Queue orderConfirmed() {
		return new Queue(Constants.QUEUE_ORDERCONFIRMED_ORDER);
	}
	
	@Bean
	Binding bindingOrderConfirmed(@Qualifier("orderConfirmed") Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(Constants.EVENT_ORDERCONFIRMED);
	}
	
	@Bean
	Queue orderPayed() {
		return new Queue(Constants.QUEUE_ORDERPAYED_ORDER);
	}
	
	@Bean
	Binding bindingOrderPayed(@Qualifier("orderPayed") Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(Constants.EVENT_ORDERPAYED);
	}
	
	@Bean
	Queue goodsCreated() {
		return new Queue(Constants.QUEUE_GOODSCREATED_ORDER);
	}
	
	@Bean
	Binding bindingGoodsCreated(@Qualifier("goodsCreated") Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(Constants.EVENT_GOODSCREATED);
	}
	
	@Bean
	Queue goodsUpdated() {
		return new Queue(Constants.QUEUE_GOODSUPDATED_ORDER);
	}
	
	@Bean
	Binding bindingGoodsUpdated(@Qualifier("goodsUpdated") Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(Constants.EVENT_GOODSUPDATED);
	}
}
