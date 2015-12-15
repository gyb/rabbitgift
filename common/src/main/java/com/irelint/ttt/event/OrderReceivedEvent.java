package com.irelint.ttt.event;

import org.springframework.context.ApplicationEvent;

public class OrderReceivedEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	
	private Long orderId;
	private Long orderBuyerId;
	private Long orderSellerId;
	private long orderMoney;

	public OrderReceivedEvent(Object source, Long orderId, Long orderBuyerId, Long orderSellerId, long orderMoney) {
		super(source);
		this.orderBuyerId = orderBuyerId;
		this.orderSellerId = orderSellerId;
		this.orderMoney = orderMoney;
	}

	public Long getOrderBuyerId() {
		return orderBuyerId;
	}
	
	public Long getOrderSellerId() {
		return orderSellerId;
	}

	public long getOrderMoney() {
		return orderMoney;
	}

	public Long getOrderId() {
		return orderId;
	}

}
