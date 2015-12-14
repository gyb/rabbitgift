package com.irelint.ttt.event;

import org.springframework.context.ApplicationEvent;

public class OrderConfirmedEvent extends ApplicationEvent {
	
	private static final long serialVersionUID = 1L;

	private Long orderId;

	public OrderConfirmedEvent(Object source, Long orderId) {
		super(source);
		this.orderId = orderId;
	}

	public Long getOrderId() {
		return this.orderId;
	}
}
