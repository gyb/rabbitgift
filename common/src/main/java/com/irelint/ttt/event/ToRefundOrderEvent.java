package com.irelint.ttt.event;

import org.springframework.context.ApplicationEvent;

public class ToRefundOrderEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;
	
	private Long orderId;
	private Long orderBuyerId;
	private long orderMoney;

	public ToRefundOrderEvent(Object source, Long orderId, Long orderBuyerId, long orderMoney) {
		super(source);
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
