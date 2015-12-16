package com.irelint.ttt.event;

import org.springframework.context.ApplicationEvent;

public class ToPayOrderEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	
	private Long orderId;
	private Long orderBuyerId;
	private long orderMoney;

	public ToPayOrderEvent(Object source, Long orderId, Long orderBuyerId, long orderMoney) {
		super(source);
		this.orderId = orderId;
		this.orderBuyerId = orderBuyerId;
		this.orderMoney = orderMoney;
	}

	public Long getOrderBuyerId() {
		return orderBuyerId;
	}

	public long getOrderMoney() {
		return orderMoney;
	}

	public Long getOrderId() {
		return orderId;
	}

}
