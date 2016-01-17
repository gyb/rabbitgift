package com.irelint.ttt.config;

import org.mvnsearch.spring.boot.dubbo.DubboAutoConfiguration;
import org.mvnsearch.spring.boot.dubbo.DubboBasedAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.irelint.ttt.api.AccountApi;
import com.irelint.ttt.api.GoodsApi;
import com.irelint.ttt.api.InventoryApi;
import com.irelint.ttt.api.OrderApi;
import com.irelint.ttt.api.UserApi;

@Configuration
@EnableConfigurationProperties(MyDubboProperties.class)
@AutoConfigureAfter(DubboAutoConfiguration.class)
public class MyDubboAutoConfiguration extends DubboBasedAutoConfiguration {

	@Autowired
	private MyDubboProperties properties;
	
    @Bean
    public ReferenceBean<UserApi> userService() {
        return getConsumerBean(UserApi.class, properties.getVersion(), properties.getTimeout());
    }
	
    @Bean
    public ReferenceBean<GoodsApi> goodsService() {
        return getConsumerBean(GoodsApi.class, properties.getVersion(), properties.getTimeout());
    }
	
    @Bean
    public ReferenceBean<OrderApi> orderService() {
        return getConsumerBean(OrderApi.class, properties.getVersion(), properties.getTimeout());
    }
	
    @Bean
    public ReferenceBean<InventoryApi> inventoryService() {
        return getConsumerBean(InventoryApi.class, properties.getVersion(), properties.getTimeout());
    }
	
    @Bean
    public ReferenceBean<AccountApi> accountService() {
        return getConsumerBean(AccountApi.class, properties.getVersion(), properties.getTimeout());
    }

}
