package com.irelint.ttt.config;

import org.mvnsearch.spring.boot.dubbo.DubboAutoConfiguration;
import org.mvnsearch.spring.boot.dubbo.DubboBasedAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.irelint.ttt.service.AccountService;
import com.irelint.ttt.service.GoodsService;
import com.irelint.ttt.service.InventoryService;
import com.irelint.ttt.service.OrderService;
import com.irelint.ttt.service.UserService;

@Configuration
@EnableConfigurationProperties(MyDubboProperties.class)
@AutoConfigureAfter(DubboAutoConfiguration.class)
public class MyDubboAutoConfiguration extends DubboBasedAutoConfiguration {

	@Autowired
	private MyDubboProperties properties;
	
    @Bean
    public ReferenceBean<UserService> userService() {
        return getConsumerBean(UserService.class, properties.getVersion(), properties.getTimeout());
    }
	
    @Bean
    public ReferenceBean<GoodsService> goodsService() {
        return getConsumerBean(GoodsService.class, properties.getVersion(), properties.getTimeout());
    }
	
    @Bean
    public ReferenceBean<OrderService> orderService() {
        return getConsumerBean(OrderService.class, properties.getVersion(), properties.getTimeout());
    }
	
    @Bean
    public ReferenceBean<InventoryService> inventoryService() {
        return getConsumerBean(InventoryService.class, properties.getVersion(), properties.getTimeout());
    }
	
    @Bean
    public ReferenceBean<AccountService> accountService() {
        return getConsumerBean(AccountService.class, properties.getVersion(), properties.getTimeout());
    }

}
